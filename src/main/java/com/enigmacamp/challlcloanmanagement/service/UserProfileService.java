package com.enigmacamp.challlcloanmanagement.service;

import com.enigmacamp.challlcloanmanagement.dto.request.RequestUserProfile;
import com.enigmacamp.challlcloanmanagement.dto.response.ResponseUserProfile;
import com.enigmacamp.challlcloanmanagement.entity.User;
import com.enigmacamp.challlcloanmanagement.entity.UserProfile;

public interface UserProfileService {
    UserProfile create(RequestUserProfile request);

    ResponseUserProfile update(Long id, RequestUserProfile request);

}
