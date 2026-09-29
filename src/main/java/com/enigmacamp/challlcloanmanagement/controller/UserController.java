package com.enigmacamp.challlcloanmanagement.controller;

import com.enigmacamp.challlcloanmanagement.dto.request.RequestUser;
import com.enigmacamp.challlcloanmanagement.dto.response.ResponseGlobal;
import com.enigmacamp.challlcloanmanagement.dto.response.ResponseUser;
import com.enigmacamp.challlcloanmanagement.entity.User;
import com.enigmacamp.challlcloanmanagement.service.UserService;
import com.enigmacamp.challlcloanmanagement.utils.constants.ResponseMessage;
import com.enigmacamp.challlcloanmanagement.utils.helper.SecurityUtils;
import com.enigmacamp.challlcloanmanagement.utils.responseBuilder.ResponseBuilder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;
    private final SecurityUtils securityUtils;

    // for admin
    @PostMapping
    public ResponseEntity<ResponseGlobal<ResponseUser>> registerUser(@RequestBody RequestUser request) {
        ResponseUser response = userService.createUser(request);

        return ResponseBuilder.buildResponse(HttpStatus.CREATED, ResponseMessage.SUCCES_CREATE_DATA, response);
    }

    @GetMapping
    public ResponseEntity<ResponseGlobal<List<ResponseUser>>> getAllUser() {
        List<ResponseUser> response = userService.getAllUser();

        return ResponseBuilder.buildResponse(HttpStatus.OK, ResponseMessage.SUCCES_GET_DATA, response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseGlobal<ResponseUser>> updateUser(
            @PathVariable Long id,
            @RequestBody RequestUser request) {
        ResponseUser response = userService.updateUser(id, request);

        return ResponseBuilder.buildResponse(HttpStatus.OK, ResponseMessage.SUCCES_UPDATE_DATA, response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseGlobal<ResponseUser>> getUserById(
            @PathVariable Long id) {
        ResponseUser response = userService.getUserById(id);

        return ResponseBuilder.buildResponse(HttpStatus.OK, ResponseMessage.SUCCES_GET_DATA, response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseGlobal<ResponseUser>> deleteUserById(
            @PathVariable Long id) {
        ResponseUser response = userService.removeUser(id);

        return ResponseBuilder.buildResponse(HttpStatus.OK, ResponseMessage.SUCCES_DELETE_DATA, response);
    }

    // for customer that authenticated
    @PutMapping("/me")
    public ResponseEntity<ResponseGlobal<ResponseUser>> updatePersonalProfile(
            @RequestBody RequestUser request) {

        User currentUser = securityUtils.getCurrentUser();
        ResponseUser response = userService.updateUser(currentUser.getId(), request);

        return ResponseBuilder.buildResponse(HttpStatus.OK, ResponseMessage.SUCCES_UPDATE_DATA, response);
    }

    @GetMapping("/me")
    public ResponseEntity<ResponseGlobal<ResponseUser>> getPersonalProfile() {

        User currentUser = securityUtils.getCurrentUser();
        ResponseUser response = userService.getUserById(currentUser.getId());

        log.debug("Current auth user : " + currentUser.getEmail());

        return ResponseBuilder.buildResponse(HttpStatus.OK, ResponseMessage.SUCCES_GET_DATA, response);
    }

    @DeleteMapping("/me")
    public ResponseEntity<ResponseGlobal<ResponseUser>> deletePersonalProfile() {

        User currentUser = securityUtils.getCurrentUser();
        ResponseUser response = userService.removeUser(currentUser.getId());

        return ResponseBuilder.buildResponse(HttpStatus.OK, ResponseMessage.SUCCES_DELETE_DATA, response);
    }
}
