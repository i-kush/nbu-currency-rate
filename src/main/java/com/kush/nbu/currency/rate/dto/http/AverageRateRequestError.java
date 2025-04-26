package com.kush.nbu.currency.rate.dto.http;

public class AverageRateRequestError {

    private final String fMessage;

    public AverageRateRequestError(String aMessage) {
        fMessage = aMessage;
    }

    public String getMessage() {
        return fMessage;
    }
}
