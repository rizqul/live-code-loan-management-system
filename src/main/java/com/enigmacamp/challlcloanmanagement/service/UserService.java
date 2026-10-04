package com.enigmacamp.challlcloanmanagement.service;

import com.enigmacamp.challlcloanmanagement.dto.request.RequestUser;
import com.enigmacamp.challlcloanmanagement.dto.request.RequestUserProfile;
import com.enigmacamp.challlcloanmanagement.dto.response.ResponseLoan;
import com.enigmacamp.challlcloanmanagement.dto.response.ResponseUser;
import com.enigmacamp.challlcloanmanagement.dto.response.ResponseUserProfile;
import com.enigmacamp.challlcloanmanagement.entity.Document;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    ResponseUser createUser(RequestUser request);

    ResponseUser updateUser(Long id, RequestUser request);

    List<ResponseUser> getAllUser();

    ResponseUser getUserById(Long id);

    ResponseUser removeUser(Long id);

    List<ResponseLoan> getUserLoans(Long id);

    Document uploadDocument(MultipartFile document, String documentType);

    ResponseUser createUserProfile(RequestUserProfile request);
}
