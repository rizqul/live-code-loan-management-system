package com.enigmacamp.challlcloanmanagement.controller;

import com.enigmacamp.challlcloanmanagement.dto.request.RequestLogin;
import com.enigmacamp.challlcloanmanagement.dto.request.RequestUser;
import com.enigmacamp.challlcloanmanagement.dto.response.ResponseGlobal;
import com.enigmacamp.challlcloanmanagement.dto.response.ResponseLogin;
import com.enigmacamp.challlcloanmanagement.dto.response.ResponseUser;
import com.enigmacamp.challlcloanmanagement.service.AuthService;
import com.enigmacamp.challlcloanmanagement.utils.constants.ResponseMessage;
import com.enigmacamp.challlcloanmanagement.utils.responseBuilder.ResponseBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ResponseGlobal<ResponseUser>> register(
            @RequestBody RequestUser request) {
        ResponseUser userResponse = authService.register(request);

        return ResponseBuilder.buildResponse(HttpStatus.CREATED, ResponseMessage.SUCCES_CREATE_DATA, userResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseGlobal<ResponseLogin>> login(
            @RequestBody RequestLogin request) {
        ResponseLogin loginResponse = authService.login(request);

        return ResponseBuilder.buildResponse(HttpStatus.OK, ResponseMessage.SUCCES_LOGIN, loginResponse);
    }
}
