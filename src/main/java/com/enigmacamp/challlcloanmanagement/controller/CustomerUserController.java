package com.enigmacamp.challlcloanmanagement.controller;

import com.enigmacamp.challlcloanmanagement.dto.request.RequestUser;
import com.enigmacamp.challlcloanmanagement.dto.request.RequestUserProfile;
import com.enigmacamp.challlcloanmanagement.dto.response.ResponseGlobal;
import com.enigmacamp.challlcloanmanagement.dto.response.ResponseLoan;
import com.enigmacamp.challlcloanmanagement.dto.response.ResponseUser;
import com.enigmacamp.challlcloanmanagement.entity.Document;
import com.enigmacamp.challlcloanmanagement.entity.Loan;
import com.enigmacamp.challlcloanmanagement.entity.User;
import com.enigmacamp.challlcloanmanagement.service.DocumentService;
import com.enigmacamp.challlcloanmanagement.service.UserService;
import com.enigmacamp.challlcloanmanagement.utils.constants.ResponseMessage;
import com.enigmacamp.challlcloanmanagement.utils.helper.SecurityUtils;
import com.enigmacamp.challlcloanmanagement.utils.mapper.MapperLoan;
import com.enigmacamp.challlcloanmanagement.utils.responseBuilder.ResponseBuilder;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customer")
@AllArgsConstructor
public class CustomerUserController {

    private final UserService userService;
    private final SecurityUtils securityUtils;
    private final DocumentService documentService;

    @PutMapping("/me")
    public ResponseEntity<ResponseGlobal<ResponseUser>> updateMyProfile(@RequestBody RequestUser request) {
        Long id = securityUtils.getCurrentUserId();
        ResponseUser response = userService.updateUser(id, request);

        return ResponseBuilder.buildResponse(HttpStatus.OK, ResponseMessage.SUCCES_UPDATE_DATA, response);
    }

    @GetMapping("/me")
    public ResponseEntity<ResponseGlobal<ResponseUser>> getMyProfile() {
        Long id = securityUtils.getCurrentUserId();
        ResponseUser response = userService.getUserById(id);

        return ResponseBuilder.buildResponse(HttpStatus.OK, ResponseMessage.SUCCES_GET_DATA, response);
    }

    @DeleteMapping("/me")
    public ResponseEntity<ResponseGlobal<ResponseUser>> deleteMyProfile() {
        Long id = securityUtils.getCurrentUserId();
        ResponseUser response = userService.removeUser(id);

        return ResponseBuilder.buildResponse(HttpStatus.OK, ResponseMessage.SUCCES_DELETE_DATA, response);
    }

    @GetMapping("/me/loans")
    public ResponseEntity<ResponseGlobal<List<ResponseLoan>>> getMyLoans() {
        Long id = securityUtils.getCurrentUserId();

        List<ResponseLoan> response = userService.getUserLoans(id);
        return ResponseBuilder.buildResponse(HttpStatus.OK, ResponseMessage.SUCCES_GET_DATA, response);
    }

    @PostMapping("/me/complete-profile")
    public ResponseEntity<ResponseGlobal<ResponseUser>> completeProfile(@RequestBody RequestUserProfile request) {

        ResponseUser response = userService.createUserProfile(request);

        return ResponseBuilder.buildResponse(HttpStatus.OK, ResponseMessage.SUCCES_UPDATE_DATA, response);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, value = "/document/ktp")
    public ResponseEntity<ResponseGlobal<Document>> uploadKTP(
            @RequestParam("document") MultipartFile document,
            @RequestParam("document_type") @NotBlank String documentType) {
        User curentUser = securityUtils.getCurrentUser();
        Document ktp = documentService.store(document, documentType);

        return ResponseBuilder.buildResponse(HttpStatus.OK, ResponseMessage.SUCCES_UPDATE_DATA, ktp);
    }

}
