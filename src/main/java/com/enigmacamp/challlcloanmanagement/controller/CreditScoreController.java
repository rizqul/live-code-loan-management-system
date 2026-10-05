package com.enigmacamp.challlcloanmanagement.controller;

import com.enigmacamp.challlcloanmanagement.dto.request.RequestCreditScore;
import com.enigmacamp.challlcloanmanagement.dto.response.ResponseCheckRestClient;
import com.enigmacamp.challlcloanmanagement.dto.response.ResponseCreditScore;
import com.enigmacamp.challlcloanmanagement.dto.response.ResponseGlobal;
import com.enigmacamp.challlcloanmanagement.service.CreditScoreService;
import com.enigmacamp.challlcloanmanagement.utils.constants.ResponseMessage;
import com.enigmacamp.challlcloanmanagement.utils.responseBuilder.ResponseBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/json/credit-score")
@RequiredArgsConstructor
@Slf4j
public class CreditScoreController {

    private final CreditScoreService creditScoreService;

    @GetMapping
    public ResponseEntity<ResponseGlobal<ResponseCheckRestClient>> healthCheck(){
        ResponseCheckRestClient response = creditScoreService.checkHealth();

        return ResponseBuilder.buildResponse(
                HttpStatus.OK,
                ResponseMessage.SUCCES_GET_DATA,
                response
        );
    }

    @PostMapping
    public ResponseEntity<ResponseGlobal<ResponseCreditScore>> requestCreditScore(@RequestBody RequestCreditScore request){
        ResponseCreditScore response = creditScoreService.getCreditScore(request);

        return ResponseBuilder.buildResponse(
                HttpStatus.OK,
                ResponseMessage.SUCCES_GET_DATA,
                response
        );
    }
}
