package com.kush.nbu.currency.rate.dto.http;

public class RawTargetDescriptor { // no prefix because it's request body

    private String currency;
    private Integer month;
    private Integer year;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String aCurrency) {
        currency = aCurrency;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer aMonth) {
        month = aMonth;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer aYear) {
        year = aYear;
    }
}
