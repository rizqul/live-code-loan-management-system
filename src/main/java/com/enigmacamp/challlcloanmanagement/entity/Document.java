package com.enigmacamp.challlcloanmanagement.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "documents")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Document extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "original_name", nullable = false)
    private String originalName;

    @Column(name = "generated_name", nullable = false, unique = true)
    private String generatedName; // nama file yang tersimpan didalam database

    @Column(name = "document_path")
    private String documentPath;

    @Column(name = "document_extension")
    private String documentExtension;

    @Column(name = "document_size")
    private Long documentSize;

    @Column(name = "document_type")
    private String documentType;

//    @Column(name = "reference_id")
//    private String referenceId;
}
