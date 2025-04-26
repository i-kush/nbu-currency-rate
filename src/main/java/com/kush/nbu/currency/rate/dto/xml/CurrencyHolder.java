package com.kush.nbu.currency.rate.dto.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "exchange")
@XmlAccessorType(XmlAccessType.FIELD)
public class CurrencyHolder {

    @XmlElement(name = "currency")
    private List<Currency> fCurrencyRates;

    public List<Currency> getCurrencyRates() {
        return fCurrencyRates;
    }

    public Currency find(String aCurrencyCod) {
        if (fCurrencyRates == null) {
            return null;
        }

        for (Currency lCurrency : fCurrencyRates) {
            if (lCurrency.getCurrencyCode().equals(aCurrencyCod)) {
                return lCurrency;
            }
        }

        return null;
    }

    @Override
    public String toString() {
        StringBuilder lStringBuilder = new StringBuilder();
        for (Currency lCurrency : fCurrencyRates) {
            lStringBuilder.append(lCurrency).append(System.lineSeparator());
        }
        return lStringBuilder.substring(0, lStringBuilder.length() - System.lineSeparator().length());
    }

    @XmlRootElement(name = "currency")
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Currency {

        @XmlElement(name = "rate")
        private double fRate;

        @XmlElement(name = "cc")
        private String fCurrencyCode;

        @XmlElement(name = "txt")
        private String fCurrencyName;

        public double getRate() {
            return fRate;
        }

        public String getCurrencyCode() {
            return fCurrencyCode;
        }

        public String getCurrencyName() {
            return fCurrencyName;
        }

        @Override
        public String toString() {
            return String.format("%s = %f UAH (%s)", fCurrencyCode, fRate, fCurrencyName);
        }
    }
}