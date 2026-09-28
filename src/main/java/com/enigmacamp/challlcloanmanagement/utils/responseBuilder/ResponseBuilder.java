package com.enigmacamp.challlcloanmanagement.utils.responseBuilder;

import com.enigmacamp.challlcloanmanagement.dto.response.ResponseGlobal;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseBuilder {
    public static <T> ResponseEntity<ResponseGlobal<T>> buildResponse(HttpStatus httpStatus, String message, T data) {
        ResponseGlobal<T> response = ResponseGlobal.<T>builder()
                .statusCode(httpStatus.value())
                .message(message)
                .data(data)
                .build();

        return ResponseEntity.status(httpStatus).body(response);
    }
}
