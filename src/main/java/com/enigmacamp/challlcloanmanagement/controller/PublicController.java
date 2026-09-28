package com.enigmacamp.challlcloanmanagement.controller;

import com.enigmacamp.challlcloanmanagement.dto.response.ResponseGlobal;
import com.enigmacamp.challlcloanmanagement.utils.constants.ResponseMessage;
import com.enigmacamp.challlcloanmanagement.utils.responseBuilder.ResponseBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/public")
public class PublicController {
    @GetMapping("/hello")
    public ResponseEntity<ResponseGlobal<String>> helloWorld() {
        return ResponseBuilder.buildResponse(HttpStatus.OK, ResponseMessage.SUCCES_GET_DATA, "Hello world!");
    }
}
