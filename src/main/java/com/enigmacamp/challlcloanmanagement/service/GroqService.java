package com.enigmacamp.challlcloanmanagement.service;

import com.enigmacamp.challlcloanmanagement.dto.request.RequestGroqEvaluation;

public interface GroqService {
    String generateEvaluation(RequestGroqEvaluation request);
}
