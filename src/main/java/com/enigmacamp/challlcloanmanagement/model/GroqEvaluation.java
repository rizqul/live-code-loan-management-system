package com.enigmacamp.challlcloanmanagement.model;


import com.enigmacamp.challlcloanmanagement.entity.enums.AiRecommendation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GroqEvaluation {
    private AiRecommendation recommendation;
    private String explanation;
}
