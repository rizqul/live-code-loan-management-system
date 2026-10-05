package com.enigmacamp.challlcloanmanagement.dto.request;


import com.enigmacamp.challlcloanmanagement.entity.enums.EmploymentStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestLoan {
    @Min(message = "the amount must be greater than zero", value = 0)
    private BigDecimal loanAmount;

    @Min(message = "the amount must be greater than zero", value = 0)
    private Long tenor;

    @NotBlank
    private String purpose;
}
