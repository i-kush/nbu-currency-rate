package com.kush.nbu.currency.rate.validators;

public interface ICurrentRateValidator<T> {

    void validate(T aValue) throws Exception;

}
