package com.enigmacamp.challlcloanmanagement.dto.response;

import com.enigmacamp.challlcloanmanagement.entity.Loan;
import com.enigmacamp.challlcloanmanagement.entity.UserProfile;
import com.enigmacamp.challlcloanmanagement.entity.enums.UserRole;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseUser {
    private Long id;
    private ResponseUserProfile userProfile;
    private String name;
    private String email;
    private String password;
    private String phoneNumber;
    private UserRole role;
    // private List<Loan> loans;
}
