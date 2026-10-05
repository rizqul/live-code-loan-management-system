package com.enigmacamp.challlcloanmanagement.controller;

import com.enigmacamp.challlcloanmanagement.dto.request.RequestGroqEvaluation;
import com.enigmacamp.challlcloanmanagement.model.GroqEvaluation;
import com.enigmacamp.challlcloanmanagement.service.GroqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/ai")
public class GroqController {
    private final GroqService groqService;

    @Autowired
    public GroqController(GroqService groqService) {
        this.groqService = groqService;
    }

    @PostMapping("/eval")
    public ResponseEntity<GroqEvaluation> generateQuiz(
            @RequestBody RequestGroqEvaluation request
    ) {


        return ResponseEntity.ok(groqService.generateEvaluation(request));
    }
}

