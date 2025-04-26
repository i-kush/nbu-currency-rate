package com.kush.nbu.currency.rate.dto.http;

public class AverageRateInfo {
    
    private final String fCurrencyCode;
    private final Double fAverageNbuRate;
    private final Integer fYear;
    private final Integer fMonth;

    public AverageRateInfo(String aCurrencyCode,
                           Double aAverageNbuRate,
                           Integer aYear,
                           Integer aMonth) {
        fCurrencyCode = aCurrencyCode;
        fAverageNbuRate = aAverageNbuRate;
        fYear = aYear;
        fMonth = aMonth;
    }

    public String getCurrencyCode() {
        return fCurrencyCode;
    }

    public Double getAverageNbuRate() {
        return fAverageNbuRate;
    }

    public Integer getYear() {
        return fYear;
    }

    public Integer getMonth() {
        return fMonth;
    }
}
