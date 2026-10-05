package com.enigmacamp.challlcloanmanagement.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseCreditScore {
    private String customerId;
    private Long score;
    private String riskLevel;
    private String isEligible;
}
