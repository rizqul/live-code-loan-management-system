package com.enigmacamp.challlcloanmanagement.utils.mapper;

import com.enigmacamp.challlcloanmanagement.dto.response.ResponseCompleteUserProfile;
import com.enigmacamp.challlcloanmanagement.entity.User;
import com.enigmacamp.challlcloanmanagement.entity.UserProfile;

import java.util.List;

public class MapperCompleteUserProfile {
    public static ResponseCompleteUserProfile convertToResponseCompleteUserProfile(User user, UserProfile userProfile) {
        return ResponseCompleteUserProfile.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .role(user.getRole())
                .employmentStatus(userProfile.getEmploymentStatus())
                .monthlyExpense(userProfile.getMonthlyExpense())
                .monthlyIncome(userProfile.getMonthlyIncome())
                .ktpPath(userProfile.getKtpPath())
                .salarySlipPath(userProfile.getSalarySlipPath())
                .build();
    }

}
