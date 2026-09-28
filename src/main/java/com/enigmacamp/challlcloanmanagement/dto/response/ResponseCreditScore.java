package com.enigmacamp.challlcloanmanagement.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseCreditScore {
    private Long id;
    private Long score;
    private String riskLevel;
    private String isEligible;
}
