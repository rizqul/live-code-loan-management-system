package com.enigmacamp.challlcloanmanagement.dto.response;


import com.enigmacamp.challlcloanmanagement.entity.enums.EmploymentStatus;
import com.enigmacamp.challlcloanmanagement.entity.enums.LoanStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseLoan {
   private Long id;
    private BigDecimal loanAmount;
    private Long tenor;
    private String purpose;
    private Long creditScore;
    private String riskLevel;
    private Boolean isEligible;
    private String aiRecommendation;
    private String aiExplanation;
    private String aiStatus;
    private LoanStatus loanStatus;
}