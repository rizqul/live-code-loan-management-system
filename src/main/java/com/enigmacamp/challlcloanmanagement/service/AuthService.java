package com.enigmacamp.challlcloanmanagement.service;

import com.enigmacamp.challlcloanmanagement.dto.request.RequestLogin;
import com.enigmacamp.challlcloanmanagement.dto.request.RequestUser;
import com.enigmacamp.challlcloanmanagement.dto.response.ResponseLogin;
import com.enigmacamp.challlcloanmanagement.dto.response.ResponseUser;
import org.springframework.security.authentication.BadCredentialsException;

public interface AuthService {
    ResponseLogin login(RequestLogin request) throws BadCredentialsException;

    ResponseUser register(RequestUser request);
}
