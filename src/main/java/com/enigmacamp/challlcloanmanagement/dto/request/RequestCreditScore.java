package com.enigmacamp.challlcloanmanagement.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestCreditScore {
    private Long id;
    private Long score;
    private String riskLevel;
    private String isEligible;
}
