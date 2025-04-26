package com.kush.nbu.currency.rate.controllers;

import com.kush.nbu.currency.rate.dto.http.AverageRateRequestError;
import com.kush.nbu.currency.rate.exceptions.AverageRateRequestException;
import com.kush.nbu.currency.rate.exceptions.RequestDataValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlingController {

    @ExceptionHandler({AverageRateRequestException.class, RequestDataValidationException.class})
    public ResponseEntity<AverageRateRequestError> badRequestBodyInfo(AverageRateRequestException aAverageRateRequestException) {
        aAverageRateRequestException.printStackTrace();
        AverageRateRequestError lRequestError = new AverageRateRequestError(aAverageRateRequestException.getMessage());

        return new ResponseEntity<>(lRequestError, HttpStatus.BAD_REQUEST);
    }
}
