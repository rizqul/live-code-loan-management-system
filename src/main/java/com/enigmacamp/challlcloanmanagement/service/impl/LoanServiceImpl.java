package com.enigmacamp.challlcloanmanagement.service.impl;

import com.enigmacamp.challlcloanmanagement.dto.request.RequestCreditScore;
import com.enigmacamp.challlcloanmanagement.dto.request.RequestGroqEvaluation;
import com.enigmacamp.challlcloanmanagement.dto.request.RequestLoan;
import com.enigmacamp.challlcloanmanagement.dto.response.ResponseCreditScore;
import com.enigmacamp.challlcloanmanagement.dto.response.ResponseLoan;
import com.enigmacamp.challlcloanmanagement.entity.Loan;
import com.enigmacamp.challlcloanmanagement.entity.User;
import com.enigmacamp.challlcloanmanagement.entity.UserProfile;
import com.enigmacamp.challlcloanmanagement.entity.enums.AiStatus;
import com.enigmacamp.challlcloanmanagement.entity.enums.LoanStatus;
import com.enigmacamp.challlcloanmanagement.model.GroqEvaluation;
import com.enigmacamp.challlcloanmanagement.repository.LoanRepository;
import com.enigmacamp.challlcloanmanagement.service.CreditScoreService;
import com.enigmacamp.challlcloanmanagement.service.GroqService;
import com.enigmacamp.challlcloanmanagement.service.LoanService;
import com.enigmacamp.challlcloanmanagement.utils.exceptions.ClientErrorException;
import com.enigmacamp.challlcloanmanagement.utils.exceptions.NotCompletedProfileException;
import com.enigmacamp.challlcloanmanagement.utils.helper.SecurityUtils;
import com.enigmacamp.challlcloanmanagement.utils.mapper.MapperLoan;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
@Slf4j
public class LoanServiceImpl implements LoanService {

    private final LoanRepository loanRepository;
    private final CreditScoreService creditScoreService;
    private final GroqService groqService;
    private final SecurityUtils securityUtils;

    @Override
    @Transactional(readOnly = false)
    public ResponseLoan createLoan(RequestLoan request) {

        User currentUser = securityUtils.getCurrentUser();

        if (currentUser.getUserProfile() == null){
            throw new NotCompletedProfileException("you not complete the user profile");
        }

        UserProfile userProfile = currentUser.getUserProfile();

        if (userProfile.getMonthlyIncome() == null){
            throw new NotCompletedProfileException("monthly income is not found");
        }

        if (userProfile.getEmploymentStatus() == null){
            throw new NotCompletedProfileException("employment status is not found");
        }

        if (userProfile.getKtpPath() == null || userProfile.getSalarySlipPath() == null){
            throw new NotCompletedProfileException("a mandatory document still not uploaded");
        }

        RequestCreditScore requestCreditScore = RequestCreditScore.builder()
                .customerId(currentUser.getId().toString())
                .monthlyIncome(userProfile.getMonthlyIncome())
                .monthlyExpense(userProfile.getMonthlyExpense())
                .requestedAmount(request.getLoanAmount())
                .tenor(request.getTenor())
                .build();

        ResponseCreditScore responseCreditScore = creditScoreService.getCreditScore(requestCreditScore);

        if(responseCreditScore == null){
            throw new ClientErrorException("client not responding");
        }

        RequestGroqEvaluation groqEvaluation = RequestGroqEvaluation.builder()
                .requestedAmount(request.getLoanAmount())
                .creditScore(responseCreditScore.getScore())
                .isEligible(responseCreditScore.getIsEligible())
                .riskLevel(responseCreditScore.getRiskLevel())
                .monthlyIncome(userProfile.getMonthlyIncome())
                .employmentStatus(userProfile.getEmploymentStatus().toString())
                .tenor(request.getTenor())
                .build();

        GroqEvaluation responseGroq = groqService.generateEvaluation(groqEvaluation);

        AiStatus aiStatus = AiStatus.FAILED;
        if (responseGroq.getRecommendation() != null || responseGroq.getExplanation() != null){
            aiStatus = AiStatus.SUCCESS;
        }

        Loan loan = Loan.builder()
                .user(currentUser)
                .loanAmount(request.getLoanAmount())
                .tenor(request.getTenor())
                .purpose(request.getPurpose())
                .creditScore(responseCreditScore.getScore())
                .riskLevel(responseCreditScore.getRiskLevel())
                .isEligible(Boolean.valueOf(responseCreditScore.getIsEligible()))
                .aiRecommendation(responseGroq.getRecommendation())
                .aiExplanation(responseGroq.getExplanation())
                .aiStatus(aiStatus)
                .aiGeneratedAt(LocalDateTime.now())
                .loanStatus(LoanStatus.PENDING)
                .build();

        Loan newLoan = loanRepository.save(loan);

        return MapperLoan.convertToResponseLoan(newLoan);
    }
}
