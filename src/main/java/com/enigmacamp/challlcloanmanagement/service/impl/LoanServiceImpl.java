package com.enigmacamp.challlcloanmanagement.service.impl;

import com.enigmacamp.challlcloanmanagement.dto.request.RequestCreditScore;
import com.enigmacamp.challlcloanmanagement.dto.request.RequestLoan;
import com.enigmacamp.challlcloanmanagement.dto.response.ResponseCreditScore;
import com.enigmacamp.challlcloanmanagement.dto.response.ResponseLoan;
import com.enigmacamp.challlcloanmanagement.entity.Loan;
import com.enigmacamp.challlcloanmanagement.entity.User;
import com.enigmacamp.challlcloanmanagement.entity.enums.EmploymentStatus;
import com.enigmacamp.challlcloanmanagement.entity.enums.LoanStatus;
import com.enigmacamp.challlcloanmanagement.model.CreditScore;
import com.enigmacamp.challlcloanmanagement.repository.LoanRepository;
import com.enigmacamp.challlcloanmanagement.service.CreditScoreService;
import com.enigmacamp.challlcloanmanagement.service.LoanService;
import com.enigmacamp.challlcloanmanagement.utils.helper.SecurityUtils;
import com.enigmacamp.challlcloanmanagement.utils.mapper.MapperLoan;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
@Slf4j
public class LoanServiceImpl implements LoanService {

    private final LoanRepository loanRepository;
    private final CreditScoreService creditScoreService;
    private final SecurityUtils securityUtils;

    @Override
    @Transactional(readOnly = false)
    public ResponseLoan createLoan(RequestLoan request) {

        User currentUser = securityUtils.getCurrentUser();

        RequestCreditScore requestCreditScore = RequestCreditScore.builder()
                .id(currentUser.getId())
                .monthlyIncome(request.getMonthlyIncome())
                .monthlyExpense(request.getMonthlyExpense())
                .requestedAmount(request.getLoanAmount())
                .tenor(request.getTenor())
                .build();

        ResponseCreditScore responseCreditScore = creditScoreService.getCreditScore(requestCreditScore);

        Long creditScore = responseCreditScore.getScore();
        String recommendation;
        if (creditScore >= 700){
            recommendation = "APPROVED";
        } else if (creditScore >= 500) {
           recommendation = "REVIEW";
        } else {
            recommendation = "REJECTED";
        }

        Loan loan = Loan.builder()
                .user(currentUser)
                .loanAmount(request.getLoanAmount())
                .tenor(request.getTenor())
                .purpose(request.getPurpose())
                .monthlyIncome(request.getMonthlyIncome())
                .employmentStatus(EmploymentStatus.valueOf(request.getEmploymentStatus()))
                .creditScore(creditScore)
                .creditRecommendation(recommendation)
                .loanStatus(LoanStatus.valueOf("PENDING"))
                .build();

        Loan newLoan = loanRepository.save(loan);
        return MapperLoan.convertToResponseLoan(newLoan);
    }
}
