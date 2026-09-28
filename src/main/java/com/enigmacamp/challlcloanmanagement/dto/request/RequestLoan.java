package com.enigmacamp.challlcloanmanagement.dto.request;


import com.enigmacamp.challlcloanmanagement.entity.enums.EmploymentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestLoan {
    private BigDecimal loanAmount;
    private Long tenor;
    private String purpose;
    private BigDecimal monthlyIncome;
    private String employmentStatus;
}
