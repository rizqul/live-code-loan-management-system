package com.enigmacamp.challlcloanmanagement.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseLogin {
    private String token;
    private String role;
    private String username;
}
