package com.enigmacamp.challlcloanmanagement.controller;

import com.enigmacamp.challlcloanmanagement.dto.request.RequestUser;
import com.enigmacamp.challlcloanmanagement.dto.response.ResponseGlobal;
import com.enigmacamp.challlcloanmanagement.dto.response.ResponseLoan;
import com.enigmacamp.challlcloanmanagement.dto.response.ResponseUser;
import com.enigmacamp.challlcloanmanagement.entity.Loan;
import com.enigmacamp.challlcloanmanagement.entity.User;
import com.enigmacamp.challlcloanmanagement.service.UserService;
import com.enigmacamp.challlcloanmanagement.utils.constants.ResponseMessage;
import com.enigmacamp.challlcloanmanagement.utils.helper.SecurityUtils;
import com.enigmacamp.challlcloanmanagement.utils.mapper.MapperLoan;
import com.enigmacamp.challlcloanmanagement.utils.responseBuilder.ResponseBuilder;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customer")
@AllArgsConstructor
public class CustomerUserController {

    private final UserService userService;
    private final SecurityUtils securityUtils;

    @PutMapping("/me")
    public ResponseEntity<ResponseGlobal<ResponseUser>> updateMyProfile(@RequestBody RequestUser request){
        Long id = securityUtils.getCurrentUserId();
        ResponseUser response = userService.updateUser(id, request);

        return ResponseBuilder.buildResponse(HttpStatus.OK, ResponseMessage.SUCCES_UPDATE_DATA, response);
    }

    @GetMapping("/me")
    public ResponseEntity<ResponseGlobal<ResponseUser>> getMyProfile(){
        Long id = securityUtils.getCurrentUserId();
        ResponseUser response = userService.getUserById(id);

        return ResponseBuilder.buildResponse(HttpStatus.OK, ResponseMessage.SUCCES_GET_DATA, response);
    }

    @DeleteMapping("/me")
    public ResponseEntity<ResponseGlobal<ResponseUser>> deleteMyProfile(){
        Long id = securityUtils.getCurrentUserId();
        ResponseUser response = userService.removeUser(id);

        return ResponseBuilder.buildResponse(HttpStatus.OK, ResponseMessage.SUCCES_DELETE_DATA, response);
    }

    @GetMapping("/me/loans")
    public ResponseEntity<ResponseGlobal<List<ResponseLoan>>> getMyLoans(){
        Long id = securityUtils.getCurrentUserId();

        List<ResponseLoan> response = userService.getUserLoans(id);
        return ResponseBuilder.buildResponse(HttpStatus.OK, ResponseMessage.SUCCES_GET_DATA, response);
    }
}
