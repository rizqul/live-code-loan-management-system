package com.enigmacamp.challlcloanmanagement.service.impl;

import com.enigmacamp.challlcloanmanagement.dto.request.RequestCreditScore;
import com.enigmacamp.challlcloanmanagement.dto.response.ResponseCheckRestClient;
import com.enigmacamp.challlcloanmanagement.dto.response.ResponseCreditScore;
import com.enigmacamp.challlcloanmanagement.service.CreditScoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;


@Service
@Slf4j
@RequiredArgsConstructor
public class CreditScoreServiceImpl implements CreditScoreService {

    private final RestClient restClient;

    @Override
    public ResponseCheckRestClient checkHealth() {

        try {
            return restClient.get()
                    .uri("/health")
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .body(ResponseCheckRestClient.class);
        } catch (Exception e) {
            log.error("error to retrieve data check heath: " + e.getMessage());
            return null;
        }
    }

    @Override
    public ResponseCreditScore getCreditScore(RequestCreditScore request) {
        try {
            return restClient.post()
                    .uri("/credit-scores")
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .body(ResponseCreditScore.class);
        } catch (Exception e){
            log.error("error to retrieve data credit score : " + e.getMessage());
            return null;
        }
    }
}
