package com.enigmacamp.challlcloanmanagement.entity;

import com.enigmacamp.challlcloanmanagement.entity.enums.AiRecommendation;
import com.enigmacamp.challlcloanmanagement.entity.enums.AiStatus;
import com.enigmacamp.challlcloanmanagement.entity.enums.EmploymentStatus;
import com.enigmacamp.challlcloanmanagement.entity.enums.LoanStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull
    @Min(0)
    private BigDecimal loanAmount;

    @NotNull
    @Min(0)
    private Long tenor;

    @NotBlank
    private String purpose;

    @NotNull
    @Min(0)
    private Long creditScore;

    @NotNull
    @NotBlank
    private String riskLevel;

    @NotNull
    private Boolean isEligible;

    private AiRecommendation aiRecommendation;
    private String aiExplanation;
    private AiStatus aiStatus;
    private LocalDateTime aiGeneratedAt;

    @NotNull
    private LoanStatus loanStatus;
}