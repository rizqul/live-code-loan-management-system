package com.enigmacamp.challlcloanmanagement.entity;

import com.enigmacamp.challlcloanmanagement.entity.enums.EmploymentStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "users_profile")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfile extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String name;

    @Min(0)
    private BigDecimal monthlyIncome;

    @Min(0)
    private BigDecimal monthlyExpense;

    private EmploymentStatus employmentStatus;

    private String ktpPath;

    private String salarySlipPath;
}
