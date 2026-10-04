package com.enigmacamp.challlcloanmanagement.service.impl;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.enigmacamp.challlcloanmanagement.dto.request.RequestUserProfile;
import com.enigmacamp.challlcloanmanagement.dto.response.ResponseUserProfile;
import com.enigmacamp.challlcloanmanagement.entity.User;
import com.enigmacamp.challlcloanmanagement.entity.UserProfile;
import com.enigmacamp.challlcloanmanagement.entity.enums.EmploymentStatus;
import com.enigmacamp.challlcloanmanagement.repository.UserProfileRepository;
import com.enigmacamp.challlcloanmanagement.service.UserProfileService;
import com.enigmacamp.challlcloanmanagement.utils.exceptions.InvalidParamUrlException;
import com.enigmacamp.challlcloanmanagement.utils.exceptions.UserProfileNotFoundException;
import com.enigmacamp.challlcloanmanagement.utils.helper.SecurityUtils;
import com.enigmacamp.challlcloanmanagement.utils.mapper.MapperUserProfile;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
@Slf4j
public class UserProfileServiceImpl implements UserProfileService {

        private final UserProfileRepository userProfileRepository;
        private final SecurityUtils securityUtils;

        @Override
        @Transactional(readOnly = false)
        public UserProfile create(RequestUserProfile request) {
                User user = securityUtils.getCurrentUser();

                UserProfile userProfile = UserProfile.builder()
                                .name(request.getName())
                                .user(user)
                                .employmentStatus(EmploymentStatus.valueOf(request.getEmploymentStatus()))
                                .monthlyExpense(request.getMonthlyExpense())
                                .monthlyIncome(request.getMonthlyIncome())
                                .ktpPath(null)
                                .salarySlipPath(null)
                                .build();

                UserProfile savedUserProfile = userProfileRepository.save(userProfile);
                return savedUserProfile;
        }

        @Override
        public ResponseUserProfile update(Long id, RequestUserProfile request) {
                if (id <= 0) {
                        throw new InvalidParamUrlException("bad path variable");
                }

                UserProfile userProfile = userProfileRepository.findById(id)
                                .orElseThrow(() -> new UserProfileNotFoundException(null));

                String name = (request.getName() != null && !request.getName().isBlank()) ? request.getName()
                                : userProfile.getName();

                String employmentStatus = (request.getEmploymentStatus() != null
                                && !request.getEmploymentStatus().isBlank())
                                                ? request.getEmploymentStatus().toUpperCase()
                                                : userProfile.getEmploymentStatus().toString();

                BigDecimal monthlyExpense = (request.getMonthlyExpense() != null) ? request.getMonthlyExpense()
                                : userProfile.getMonthlyExpense();

                BigDecimal monthlyIncome = (request.getMonthlyIncome() != null) ? request.getMonthlyIncome()
                                : userProfile.getMonthlyIncome();

                String ktpPath = (request.getKtpPath() != null && !request.getKtpPath().isBlank())
                                ? request.getKtpPath()
                                : userProfile.getKtpPath();

                String salarySlipPath = (request.getSalarySlipPath() != null && !request.getSalarySlipPath().isBlank())
                                ? request.getSalarySlipPath()
                                : userProfile.getSalarySlipPath();

                userProfile.setName(name);
                userProfile.setEmploymentStatus(EmploymentStatus.valueOf(employmentStatus));
                userProfile.setMonthlyExpense(monthlyExpense);
                userProfile.setMonthlyIncome(monthlyIncome);
                userProfile.setKtpPath(ktpPath);
                userProfile.setSalarySlipPath(salarySlipPath);

                UserProfile updatedUserProfile = userProfileRepository.save(userProfile);
                return MapperUserProfile.convertToResponseUserProfile(updatedUserProfile);
        }

}
