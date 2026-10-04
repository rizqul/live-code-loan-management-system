package com.enigmacamp.challlcloanmanagement.repository;

import com.enigmacamp.challlcloanmanagement.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface DocumentRepository extends JpaRepository<Document, Long>, JpaSpecificationExecutor<Document> {
    Optional<Document> getDocumentByDocumentPath(String documentPath);
}
