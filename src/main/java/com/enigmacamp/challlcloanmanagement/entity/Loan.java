package com.enigmacamp.challlcloanmanagement.entity;

import com.enigmacamp.challlcloanmanagement.entity.enums.EmploymentStatus;
import com.enigmacamp.challlcloanmanagement.entity.enums.LoanStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "loans")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Loan extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @Min(0)
    private BigDecimal loanAmount;

    @Min(0)
    private Long tenor;

    private String purpose;

    @Min(0)
    private BigDecimal monthlyIncome;

    private EmploymentStatus employmentStatus;

    private LoanStatus loanStatus;

    @Min(0)
    private Long creditScore;

    private LoanStatus creditRecommendation;
}
