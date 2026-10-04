package com.enigmacamp.challlcloanmanagement.dto.request;

import java.math.BigDecimal;

import com.enigmacamp.challlcloanmanagement.entity.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestUserProfile {
    private String name;

    @Min(0)
    private BigDecimal monthlyIncome;

    @Min(0)
    private BigDecimal monthlyExpense;

    @NotBlank
    private String employmentStatus;

    private String ktpPath;

    private String salarySlipPath;
}
