package com.enigmacamp.challlcloanmanagement.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreditScore {
    private Long id;
    private Long score;
    private String riskLevel;
    private Boolean isEligible;
}
