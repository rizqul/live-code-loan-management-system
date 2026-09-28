package com.enigmacamp.challlcloanmanagement.service;

import com.enigmacamp.challlcloanmanagement.dto.request.RequestLoan;
import com.enigmacamp.challlcloanmanagement.dto.response.ResponseLoan;

public interface LoanService {
    ResponseLoan createLoan(RequestLoan request);
}
