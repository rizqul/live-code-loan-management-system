package com.enigmacamp.challlcloanmanagement.utils.mapper;

import com.enigmacamp.challlcloanmanagement.dto.response.ResponseLoan;
import com.enigmacamp.challlcloanmanagement.entity.Loan;

import java.util.List;

public class MapperLoan {
    public static ResponseLoan convertToResponseLoan(Loan loan){

        return ResponseLoan.builder()
                .id(loan.getId())
                .loanAmount(loan.getLoanAmount())
                .tenor(loan.getTenor())
                .purpose(loan.getPurpose())
                .creditScore(loan.getCreditScore())
                .riskLevel(loan.getRiskLevel())
                .isEligible(loan.getIsEligible())
                .aiRecommendation(loan.getAiRecommendation().toString())
                .aiExplanation(loan.getAiExplanation())
                .aiStatus(loan.getAiStatus().toString())
                .loanStatus(loan.getLoanStatus())
                .build();
    }

    public static List<ResponseLoan> convertToListResponseLoan(List<Loan> users){
        return users.stream()
                .map(MapperLoan::convertToResponseLoan)
                .toList();
    }
}

