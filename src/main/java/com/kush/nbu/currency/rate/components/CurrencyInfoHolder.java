package com.kush.nbu.currency.rate.components;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class CurrencyInfoHolder {

    private final List<CurrencyInfo> fCurrencyInfoList;

    public CurrencyInfoHolder() {
        fCurrencyInfoList = new ArrayList<>();
        loadCurrencyInfo();
    }

    private void loadCurrencyInfo() {
        try (InputStream lInputStream = this.getClass().getClassLoader().getResourceAsStream("currency.info");
             BufferedReader lBufferedReader = new BufferedReader(new InputStreamReader(lInputStream, StandardCharsets.UTF_8))) {

            String lLine;
            String[] lParts;
            while ((lLine = lBufferedReader.readLine()) != null) {
                lParts = lLine.split("=");
                fCurrencyInfoList.add(new CurrencyInfo(lParts[0], lParts[1]));
            }
        } catch (IOException aE) {
            throw new UncheckedIOException("Can't initialize resource", aE);
        }
    }

    public List<CurrencyInfo> getCurrencyInfo() {
        return fCurrencyInfoList;
    }

    public static class CurrencyInfo {

        private final String fCode;
        private final String fText;

        private CurrencyInfo(String aCode, String aText) {
            fCode = aCode;
            fText = aText;
        }

        public String getCode() {
            return fCode;
        }

        public String getText() {
            return fText;
        }
    }
}
