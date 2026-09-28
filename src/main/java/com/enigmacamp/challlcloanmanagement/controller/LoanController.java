package com.enigmacamp.challlcloanmanagement.controller;


import com.enigmacamp.challlcloanmanagement.dto.request.RequestLoan;
import com.enigmacamp.challlcloanmanagement.dto.request.RequestUser;
import com.enigmacamp.challlcloanmanagement.dto.response.ResponseGlobal;
import com.enigmacamp.challlcloanmanagement.dto.response.ResponseLoan;
import com.enigmacamp.challlcloanmanagement.dto.response.ResponseUser;
import com.enigmacamp.challlcloanmanagement.service.LoanService;
import com.enigmacamp.challlcloanmanagement.utils.constants.ResponseMessage;
import com.enigmacamp.challlcloanmanagement.utils.responseBuilder.ResponseBuilder;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/loans")
@AllArgsConstructor
public class LoanController {
    private final LoanService loanService;

    @PostMapping
    public ResponseEntity<ResponseGlobal<ResponseLoan>> createLoan(@RequestBody RequestLoan request){
        ResponseLoan response = loanService.createLoan(request);

        return ResponseBuilder.buildResponse(HttpStatus.CREATED, ResponseMessage.SUCCES_CREATE_DATA, response);
    }

}
