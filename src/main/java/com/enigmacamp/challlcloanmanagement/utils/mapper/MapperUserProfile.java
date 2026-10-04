package com.enigmacamp.challlcloanmanagement.utils.mapper;

import java.util.List;

import com.enigmacamp.challlcloanmanagement.dto.response.ResponseUserProfile;
import com.enigmacamp.challlcloanmanagement.entity.UserProfile;

public class MapperUserProfile {
    public static ResponseUserProfile convertToResponseUserProfile(UserProfile userProfile) {

        return ResponseUserProfile.builder()
                .id(userProfile.getId())
                .name(userProfile.getName())
                .userId(userProfile.getUser().getId())
                .employmentStatus(userProfile.getEmploymentStatus())
                .monthlyExpense(userProfile.getMonthlyExpense())
                .monthlyIncome(userProfile.getMonthlyIncome())
                .ktpPath(userProfile.getKtpPath())
                .salarySlipPath(userProfile.getSalarySlipPath())
                .build();
    }

    public static List<ResponseUserProfile> convertToListResponseUserProfile(List<UserProfile> users) {
        return users.stream()
                .map(MapperUserProfile::convertToResponseUserProfile)
                .toList();
    }
}
