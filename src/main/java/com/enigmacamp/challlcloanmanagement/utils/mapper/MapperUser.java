package com.enigmacamp.challlcloanmanagement.utils.mapper;

import com.enigmacamp.challlcloanmanagement.dto.response.ResponseUser;
import com.enigmacamp.challlcloanmanagement.entity.User;

import java.util.List;

public class MapperUser {
    public static ResponseUser convertToResponseUser(User user) {

        return ResponseUser.builder()
                .id(user.getId())
                .userProfile(MapperUserProfile.convertToResponseUserProfile(user.getUserProfile()))
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .phoneNumber(user.getPhoneNumber())
                .role(user.getRole())
                .build();
    }

    public static List<ResponseUser> convertToListResponseUser(List<User> users) {
        return users.stream()
                .map(MapperUser::convertToResponseUser)
                .toList();
    }
}
