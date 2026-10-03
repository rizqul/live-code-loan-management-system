package com.enigmacamp.challlcloanmanagement.service;

import com.enigmacamp.challlcloanmanagement.dto.request.RequestUser;
import com.enigmacamp.challlcloanmanagement.dto.response.ResponseLoan;
import com.enigmacamp.challlcloanmanagement.dto.response.ResponseUser;

import java.util.List;

public interface UserService {
    ResponseUser createUser(RequestUser request);
    ResponseUser updateUser(Long id, RequestUser request);
    List<ResponseUser> getAllUser();
    ResponseUser getUserById(Long id);
    ResponseUser removeUser (Long id);
    List<ResponseLoan> getUserLoans (Long id);
}
