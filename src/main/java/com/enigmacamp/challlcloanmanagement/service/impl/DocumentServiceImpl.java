package com.enigmacamp.challlcloanmanagement.service.impl;

import com.enigmacamp.challlcloanmanagement.entity.Document;
import com.enigmacamp.challlcloanmanagement.repository.DocumentRepository;
import com.enigmacamp.challlcloanmanagement.service.DocumentService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository documentRepository;

    @Value("${app.upload.dir:./uploads}")
    private String uploadDir;

    private Path documentLocation;

    @PostConstruct
    public void init() {
        try {
            this.documentLocation = Paths.get(uploadDir).toAbsolutePath().normalize();

            // validasi kalau folder sudah ada, nggak perlu di create
            if(!Files.exists(this.documentLocation)){
                Files.createDirectory(this.documentLocation);
            }
            log.info("Direktory sudah berhasil dibuat di " + this.documentLocation);

        } catch (Exception e) {
            throw new RuntimeException("Gagal dalam proses pembuatan direktori : " + e.getMessage());
        }
    }
    @Override
    public Document store(MultipartFile document, String documentType) {
        return null;
    }

    @Override
    public Resource loadFileAsResource(Long id) {
        return null;
    }

    @Override
    public Document getFileById(Long id) {
        return null;
    }

    @Override
    public void deleteFileById(Long id) {

    }
}
