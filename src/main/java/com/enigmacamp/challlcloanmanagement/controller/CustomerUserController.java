package com.enigmacamp.challlcloanmanagement.controller;

import com.enigmacamp.challlcloanmanagement.dto.request.RequestLoan;
import com.enigmacamp.challlcloanmanagement.dto.request.RequestUser;
import com.enigmacamp.challlcloanmanagement.dto.request.RequestUserProfile;
import com.enigmacamp.challlcloanmanagement.dto.response.*;
import com.enigmacamp.challlcloanmanagement.entity.Document;
import com.enigmacamp.challlcloanmanagement.entity.Loan;
import com.enigmacamp.challlcloanmanagement.entity.User;
import com.enigmacamp.challlcloanmanagement.service.DocumentService;
import com.enigmacamp.challlcloanmanagement.service.LoanService;
import com.enigmacamp.challlcloanmanagement.service.UserService;
import com.enigmacamp.challlcloanmanagement.utils.constants.ResponseMessage;
import com.enigmacamp.challlcloanmanagement.utils.helper.SecurityUtils;
import com.enigmacamp.challlcloanmanagement.utils.mapper.MapperLoan;
import com.enigmacamp.challlcloanmanagement.utils.responseBuilder.ResponseBuilder;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customer")
@AllArgsConstructor
@Slf4j
public class CustomerUserController {

    private final UserService userService;
    private final SecurityUtils securityUtils;
    private final DocumentService documentService;
    private final LoanService loanService;

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

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, value = "/document")
    public ResponseEntity<ResponseGlobal<ResponseUserProfile>> uploadDocument(
            @RequestParam("document") MultipartFile document,
            @RequestParam("document_type") @NotBlank String documentType) {
//        Document ktp = documentService.store(document, documentType);
        ResponseUserProfile response = userService.uploadDocument(document, documentType);

        return ResponseBuilder.buildResponse(HttpStatus.OK, ResponseMessage.SUCCES_UPDATE_DATA, response);
    }

    @GetMapping("/document")
    public ResponseEntity<Resource> downloadDocument (@RequestParam("document_type") @NotBlank String documentType) {

        ResponseDocument response = userService.downloadDocument(documentType);

        Resource resource = documentService.loadFileAsResource(response.getDocumentPath());

        log.error("hasil perbadingan : "+(resource.equals(response.getResource())));

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(response.getDocumentExtension()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attaachment; filename=\"" + response.getDocumentName() + "\"")
                .body(resource);
    }

    @PostMapping("/create-loan")
    public ResponseEntity<ResponseGlobal<ResponseLoan>> createLoan(@RequestBody RequestLoan request){
        ResponseLoan response = loanService.createLoan(request);

        return ResponseBuilder.buildResponse(HttpStatus.CREATED, ResponseMessage.SUCCES_CREATE_DATA, response);
    }


}
