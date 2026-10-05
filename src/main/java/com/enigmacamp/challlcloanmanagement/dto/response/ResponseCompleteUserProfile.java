package com.enigmacamp.challlcloanmanagement.dto.response;


import com.enigmacamp.challlcloanmanagement.entity.enums.EmploymentStatus;
import com.enigmacamp.challlcloanmanagement.entity.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseCompleteUserProfile {
    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    private UserRole role;
    private BigDecimal monthlyIncome;
    private BigDecimal monthlyExpense;
    private EmploymentStatus employmentStatus;
    private String ktpPath;
    private String salarySlipPath;
}
