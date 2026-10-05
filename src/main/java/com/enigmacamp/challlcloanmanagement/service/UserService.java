package com.enigmacamp.challlcloanmanagement.service;

import com.enigmacamp.challlcloanmanagement.dto.request.RequestUser;
import com.enigmacamp.challlcloanmanagement.dto.request.RequestUserProfile;
import com.enigmacamp.challlcloanmanagement.dto.response.*;
import com.enigmacamp.challlcloanmanagement.entity.Document;

import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    ResponseUser createUser(RequestUser request);

    ResponseUser updateUser(Long id, RequestUser request);

    List<ResponseUser> getAllUser();

    ResponseCompleteUserProfile getUserById(Long id);

    ResponseUser removeUser(Long id);

    List<ResponseLoan> getUserLoans(Long id);

    ResponseUserProfile uploadDocument(MultipartFile document, String documentType);

    ResponseDocument downloadDocument(String documentType);

    ResponseUser createUserProfile(RequestUserProfile request);
}
