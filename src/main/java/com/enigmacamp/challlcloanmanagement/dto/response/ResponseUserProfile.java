package com.enigmacamp.challlcloanmanagement.dto.response;

import java.math.BigDecimal;

import com.enigmacamp.challlcloanmanagement.entity.Loan;
import com.enigmacamp.challlcloanmanagement.entity.enums.EmploymentStatus;
import com.enigmacamp.challlcloanmanagement.entity.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseUserProfile {
    private Long id;

    private Long userId;

    private String name;

    private BigDecimal monthlyIncome;

    private BigDecimal monthlyExpense;

    private EmploymentStatus employmentStatus;

    private String ktpPath;

    private String salarySlipPath;
}
