package com.enigmacamp.challlcloanmanagement.service;

import com.enigmacamp.challlcloanmanagement.dto.request.RequestCreditScore;
import com.enigmacamp.challlcloanmanagement.dto.response.ResponseCheckRestClient;
import com.enigmacamp.challlcloanmanagement.dto.response.ResponseCreditScore;

public interface CreditScoreService {
    ResponseCheckRestClient checkHealth();
    ResponseCreditScore getCreditScore(RequestCreditScore request);
}
