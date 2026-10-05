package com.enigmacamp.challlcloanmanagement.service.impl;

import com.enigmacamp.challlcloanmanagement.dto.request.RequestGroqEvaluation;
import com.enigmacamp.challlcloanmanagement.dto.response.ResponseGroq;
import com.enigmacamp.challlcloanmanagement.model.GroqEvaluation;
import com.enigmacamp.challlcloanmanagement.service.GroqService;
import com.enigmacamp.challlcloanmanagement.utils.exceptions.FailedCreateGroqEvaluationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import tools.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class GroqServiceImpl implements GroqService {

    private final RestClient groqClient;

    @Value("${groq.api.model}")
    private String model;

    @Value("${groq.api.url}")
    private String apiUrl;

    @Value("${groq.ai.key}")
    private String apiKey;


    @Autowired
    private ObjectMapper objectMapper;

    public GroqServiceImpl(
            @Qualifier("groqClient") RestClient groqClient) {
        this.groqClient = groqClient;
    }

    @Override
    public GroqEvaluation generateEvaluation(RequestGroqEvaluation request) {
        try {
            String systemPrompt = """
                    you are a loan analysis assistant for loan service management system.
                    
                    give the evaluation output in valid JSON Format without addition text or markdown annotation.
                    
                    the JSON format need to follow this structure : 
                    {
                    
                          "recommendation": "<recommendation>",
                          "explanation": "the reason why it use the following <recommendation>."
                    }
                    
                    IMPORTATNT :
                    - the <recommendation> is can only be either one of these values :
                        1. APPROVE
                        2. REVIEW
                        3. REJECT
                    - the explanation is max 100 character
                    """;

            String userPrompt = """
                    give me the recommendation if should i give the loan or nah based on this data :
                        requestedAmount = Rp. %s
                        tenor = %d
                        monthlyIncome = Rp. %s
                        employmentStatus = %s
                        creditScore = %d
                        riskLevel = %s
                        eligible = %s
                    """.formatted(
                            request.getRequestedAmount(),
                    request.getTenor(),
                    request.getMonthlyIncome(),
                    request.getEmploymentStatus(),
                    request.getCreditScore(),
                    request.getRiskLevel(),
                    request.getIsEligible()
                    );

            List<Map<String, String>> messages = List.of(
                    Map.of("role", "system", "content", systemPrompt),
            Map.of("role", "user", "content", userPrompt)
            );

            Map<String, Object> requestBody = Map.of(
                    "model", model,
                    "messages", messages,
                    "response_format", Map.of("type", "json_object"),
                    "temperature", 0.4
            );

            ResponseGroq response = groqClient.post()
                    .uri(apiUrl)
                    .header("Authorization", "Bearer " + apiKey)
                    .body(requestBody)
                    .retrieve()
                    .body(ResponseGroq.class);

            if (response == null || response.getChoices() == null || response.getChoices().isEmpty()) {
                throw new RuntimeException("Groq API return null content");
            }

            GroqEvaluation evaluation;
            try {
                evaluation = objectMapper.readValue(response
                        .getChoices()
                        .get(0)
                        .getMessage()
                        .getContent(), GroqEvaluation.class);
            } catch (Exception e){
                log.error(e.getMessage());
                throw new FailedCreateGroqEvaluationException("failed to create ai recommendation");
            }

            return evaluation;

        } catch (Exception e) {
            log.error("Failed to generate quiz form groq");
            throw new RuntimeException("Failed to generate using groq api" + e.getMessage());

        }
    }
}
