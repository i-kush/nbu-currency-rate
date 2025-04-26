package com.kush.nbu.currency.rate.exceptions;

public class AverageRateRequestException extends RuntimeException {

    public AverageRateRequestException(String aMessage) {
        super(aMessage);
    }

    public AverageRateRequestException(String aMessage, Throwable aCause) {
        super(aMessage, aCause);
    }
}
