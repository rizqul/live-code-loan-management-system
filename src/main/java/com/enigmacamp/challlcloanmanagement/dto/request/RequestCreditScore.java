package com.enigmacamp.challlcloanmanagement.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestCreditScore {
    private Long id;
    private BigDecimal monthlyIncome;
    private BigDecimal monthlyExpense;
    private BigDecimal requestedAmount;
    private Long tenor;
}
