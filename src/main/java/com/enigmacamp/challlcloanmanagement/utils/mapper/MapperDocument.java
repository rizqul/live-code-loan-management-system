package com.enigmacamp.challlcloanmanagement.utils.mapper;

import com.enigmacamp.challlcloanmanagement.dto.response.ResponseDocument;
import com.enigmacamp.challlcloanmanagement.entity.Document;

import java.util.List;

public class MapperDocument {
    public static ResponseDocument convertToResponseDocument(Document document) {

        return ResponseDocument.builder()
                .id(document.getId())
                .documentExtension(document.getDocumentExtension())
                .documentName(document.getGeneratedName())
                .documentPath(document.getDocumentPath())
                .documentSize(document.getDocumentSize())
                .documentType(document.getDocumentType())
                .build();
    }

    public static List<ResponseDocument> convertToListResponseDocument(List<Document> documents) {
        return documents.stream()
                .map(MapperDocument::convertToResponseDocument)
                .toList();
    }
}

