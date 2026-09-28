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
                .monthlyIncome(loan.getMonthlyIncome())
                .employmentStatus(loan.getEmploymentStatus())
                .creditScore(loan.getCreditScore())
                .status(loan.getLoanStatus())
                .build();
    }

    public static List<ResponseLoan> convertToListResponseLoan(List<Loan> users){
        return users.stream()
                .map(MapperLoan::convertToResponseLoan)
                .toList();
    }
}

