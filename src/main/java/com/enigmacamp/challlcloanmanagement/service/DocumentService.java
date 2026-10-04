package com.enigmacamp.challlcloanmanagement.service;

import com.enigmacamp.challlcloanmanagement.entity.Document;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface DocumentService {
    Document store(MultipartFile document, String documentType);
    Resource loadFileAsResource(Long id);
    Document getFileById(Long id);
    void deleteFileById(Long id);
}
