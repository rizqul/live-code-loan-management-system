package com.enigmacamp.challlcloanmanagement.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class RequestGroqEvaluation {
    @Min(0)
    private BigDecimal requestedAmount;
    @Min(0)
    private Long tenor;
    @Min(0)
    private BigDecimal monthlyIncome;
    @NotBlank
    private String employmentStatus;
    private Long creditScore;
    @NotBlank
    private String riskLevel;
    @NotBlank
    private String isEligible;
}
