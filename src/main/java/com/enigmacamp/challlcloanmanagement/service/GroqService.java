package com.enigmacamp.challlcloanmanagement.service;

import com.enigmacamp.challlcloanmanagement.dto.request.RequestGroqEvaluation;
import com.enigmacamp.challlcloanmanagement.model.GroqEvaluation;

public interface GroqService {
    GroqEvaluation generateEvaluation(RequestGroqEvaluation request);
}
