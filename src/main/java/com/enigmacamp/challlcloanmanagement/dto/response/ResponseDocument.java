package com.enigmacamp.challlcloanmanagement.dto.response;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.core.io.Resource;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseDocument {

    private Long id;

    private String documentName;

    private String documentPath;

    private String documentExtension;

    private Long documentSize;

    private String documentType;

    private Resource resource;

}
