package com.enigmacamp.challlcloanmanagement.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestLogin {
    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;
}
