package com.kush.nbu.currency.rate.exceptions;

public class RequestDataValidationException extends AverageRateRequestException {

    public RequestDataValidationException(String aMessage) {
        super(aMessage);
    }

    public RequestDataValidationException(String aMessage, Throwable aCause) {
        super(aMessage, aCause);
    }
}
