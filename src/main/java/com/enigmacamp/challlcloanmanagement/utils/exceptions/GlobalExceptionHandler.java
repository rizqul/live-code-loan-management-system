package com.enigmacamp.challlcloanmanagement.utils.exceptions;

import com.enigmacamp.challlcloanmanagement.dto.response.ResponseGlobal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ResponseGlobal<Object>> handlerUserNotFoundException(UserNotFoundException e, WebRequest request){
        ResponseGlobal<Object> response =ResponseGlobal.builder()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .message(e.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(InvalidParamUrlException.class)
    public ResponseEntity<ResponseGlobal<Object>> handlerInvalidParamUrlException(InvalidParamUrlException e, WebRequest request){
        ResponseGlobal<Object> response =ResponseGlobal.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .message(e.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(ClientErrorException.class)
    public ResponseEntity<ResponseGlobal<Object>> handlerClientErrorException(ClientErrorException e, WebRequest request){
        ResponseGlobal<Object> response =ResponseGlobal.builder()
                .statusCode(HttpStatus.SERVICE_UNAVAILABLE.value())
                .message(e.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response);
    }


}
