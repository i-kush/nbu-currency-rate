package com.kush.nbu.currency.rate.controllers;

import com.kush.nbu.currency.rate.components.CurrencyInfoHolder;
import com.kush.nbu.currency.rate.components.CurrencyRateHandler;
import com.kush.nbu.currency.rate.dto.http.AverageRateInfo;
import com.kush.nbu.currency.rate.dto.http.RawTargetDescriptor;
import com.kush.nbu.currency.rate.dto.http.TargetDescriptor;
import com.kush.nbu.currency.rate.validators.TargetDescriptorValidator;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/monthly-rate")
public class MonthlyRateController {

    private final CurrencyRateHandler fCurrencyRateHandler;
    private final CurrencyInfoHolder fCurrencyInfoHolder;

    @Autowired
    public MonthlyRateController(CurrencyRateHandler aCurrencyRateHandler, CurrencyInfoHolder aCurrencyInfoHolder) {
        fCurrencyRateHandler = aCurrencyRateHandler;
        fCurrencyInfoHolder = aCurrencyInfoHolder;
    }

    @PostMapping(value = "calculate-rate", produces = "application/json")
    public AverageRateInfo getAverageRate(@RequestBody RawTargetDescriptor aRawTargetDescriptor) throws IOException {
        long lBefore = System.currentTimeMillis();

        TargetDescriptorValidator lTargetDescriptorValidator = new TargetDescriptorValidator();
        lTargetDescriptorValidator.validate(aRawTargetDescriptor);

        TargetDescriptor lTargetDescriptor = new TargetDescriptor(aRawTargetDescriptor);
        System.out.println(lTargetDescriptor);
        double lResult = fCurrencyRateHandler.calculateAverageCurrencyRate(lTargetDescriptor);

        System.out.printf("done in %d millis%n", (System.currentTimeMillis() - lBefore));

        return new AverageRateInfo(
                lTargetDescriptor.getCurrency(),
                lResult,
                Integer.valueOf(lTargetDescriptor.getYear()),
                Integer.valueOf(lTargetDescriptor.getMonth())
        );
    }

    @PostMapping(value = "refresh-data", produces = "application/json")
    public void refreshData(@RequestBody RawTargetDescriptor aRawTargetDescriptor) throws IOException {
        TargetDescriptor lTargetDescriptor = new TargetDescriptor(aRawTargetDescriptor);
        fCurrencyRateHandler.getFreshData(lTargetDescriptor);
    }

    @GetMapping(value = "currencies-info", produces = "application/json")
    public List<CurrencyInfoHolder.CurrencyInfo> getCurrenciesInfo() {
        return fCurrencyInfoHolder.getCurrencyInfo();
    }
}