package com.enigmacamp.challlcloanmanagement.service.impl;

import com.enigmacamp.challlcloanmanagement.dto.response.ResponseDocument;
import com.enigmacamp.challlcloanmanagement.entity.Document;
import com.enigmacamp.challlcloanmanagement.repository.DocumentRepository;
import com.enigmacamp.challlcloanmanagement.service.DocumentService;
import com.enigmacamp.challlcloanmanagement.utils.exceptions.DocumentNotFoundException;
import com.enigmacamp.challlcloanmanagement.utils.exceptions.FailedGenerateFileException;
import com.enigmacamp.challlcloanmanagement.utils.mapper.MapperDocument;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

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
            if (!Files.exists(this.documentLocation)) {
                Files.createDirectory(this.documentLocation);
            }
            log.info("Direktory sudah berhasil dibuat di " + this.documentLocation);

        } catch (Exception e) {
            throw new RuntimeException("Gagal dalam proses pembuatan direktori : " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public Document store(MultipartFile document, String documentType) {
        try {
            validateFile(document);

            String originalFileName = StringUtils.cleanPath(Objects.requireNonNull(document.getOriginalFilename()));
            String fileExtension = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
            String generatedFileName = UUID.randomUUID() + "." + fileExtension;

            if (originalFileName.contains("..")) {
                throw new BadRequestException("nama file is not valid");
            }

            Path targetLocation = this.documentLocation.resolve(generatedFileName);

            Files.copy(document.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            Document storedDocument = Document.builder()
                    .originalName(originalFileName)
                    .generatedName(generatedFileName)
                    .documentPath(targetLocation.toString())
                    .documentType(documentType)
                    .documentExtension(document.getContentType())
                    .documentSize(document.getSize())
                    .build();

            return documentRepository.save(storedDocument);

        } catch (Exception e) {
            throw new RuntimeException("gagal menyimpan file : " + document.getOriginalFilename() + e.getMessage());
        }
    }

    @Override
    public Resource loadFileAsResource(String generatedName) {
        try {
            Path documentPath = this.documentLocation.resolve(generatedName).normalize();

            Resource resource = new UrlResource(documentPath.toUri());

            if (resource.exists()){
                return  resource;
            } else {
                throw new FailedGenerateFileException("failed generated file");
            }

        } catch (MalformedURLException e){
            throw new FailedGenerateFileException("there is problem creating a file");
        }
    }

    @Override
    public Document getFileById(Long id) {
        return null;
    }

    @Override
    public void deleteFileById(Long id) {

    }

    @Override
    public Document getFileByPath(String fileUri) {

        Document document = documentRepository.getDocumentByDocumentPath(fileUri).orElseThrow(() -> new DocumentNotFoundException("Document not found"));

        return document;
    }

    private void validateFile(MultipartFile file) throws BadRequestException {
        // validasi apakah file kosong
        if (file == null || file.isEmpty()) {
            throw new BadRequestException("File tidak boleh kosong!");
        }

        // validasi tipe file -> apa saja tipe file yang diizinkan untuk disimpan
        String contentType = file.getContentType();
        List<String> allowedTypes = Arrays.asList(
                "image/jpeg", "image/jpg", "image/png", "image/gif", "application/pdf", "application/msword");

        if (contentType == null || !allowedTypes.contains(contentType)) {
            throw new BadRequestException(
                    "Tipe file tidak didukung. Hanya mendukung file bertipe JPEG, JPG, GIF, PNG, PDF, DOC");
        }

        // validasi ukuran
        if (file.getSize() > 10 * 1024 * 1024) {// 10 MB
            throw new BadRequestException("Ukuran file terlalu besar. Maksimal ukuran file 10MB");
        }
    }
}
