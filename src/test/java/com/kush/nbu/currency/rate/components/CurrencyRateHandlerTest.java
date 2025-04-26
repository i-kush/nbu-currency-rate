package com.kush.nbu.currency.rate.components;

import com.kush.nbu.currency.rate.dto.http.RawTargetDescriptor;
import com.kush.nbu.currency.rate.dto.http.TargetDescriptor;
import com.kush.nbu.currency.rate.exceptions.AverageRateRequestException;
import com.kush.nbu.currency.rate.helpers.Rates;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.mock.jndi.SimpleNamingContextBuilder;

import javax.naming.NamingException;
import java.io.IOException;
import java.lang.reflect.Field;

public class CurrencyRateHandlerTest {

    private static final double DELTA = 000000.1;
    private static final int YEAR_2018 = 2018;

    @Test
    public void rateJan2018() throws IOException {
        Rates lRate = Rates.JAN;

        CurrencyRateHandler lCurrencyRateHandler = new CurrencyRateHandler();
        TargetDescriptor lTargetDescriptor = getTargetDescriptor(YEAR_2018, lRate.getMonth());
        double lResult = lCurrencyRateHandler.getFreshData(lTargetDescriptor);
        Assert.assertEquals(lRate.getRate(), lResult, DELTA);
    }

    @Test
    public void rateMar2018() throws IOException {
        Rates lRate = Rates.MAR;

        CurrencyRateHandler lCurrencyRateHandler = new CurrencyRateHandler();
        TargetDescriptor lTargetDescriptor = getTargetDescriptor(YEAR_2018, lRate.getMonth());
        double lResult = lCurrencyRateHandler.getFreshData(lTargetDescriptor);
        Assert.assertEquals(lRate.getRate(), lResult, DELTA);
    }

    @Test
    public void rateApr2018() throws IOException {
        Rates lRate = Rates.APR;

        CurrencyRateHandler lCurrencyRateHandler = new CurrencyRateHandler();
        TargetDescriptor lTargetDescriptor = getTargetDescriptor(YEAR_2018, lRate.getMonth());
        double lResult = lCurrencyRateHandler.getFreshData(lTargetDescriptor);
        Assert.assertEquals(lRate.getRate(), lResult, DELTA);
    }

    @Test
    public void rateSep2018() throws IOException {
        Rates lRate = Rates.SEP;

        CurrencyRateHandler lCurrencyRateHandler = new CurrencyRateHandler();
        TargetDescriptor lTargetDescriptor = getTargetDescriptor(YEAR_2018, lRate.getMonth());
        double lResult = lCurrencyRateHandler.getFreshData(lTargetDescriptor);
        Assert.assertEquals(lRate.getRate(), lResult, DELTA);
    }

    @Test(expected = AverageRateRequestException.class)
    public void nanResultTest() throws IOException {
        CurrencyRateHandler lCurrencyRateHandler = new CurrencyRateHandler();
        TargetDescriptor lTargetDescriptor = getTargetDescriptor("UAH", 2017, 12);
        lCurrencyRateHandler.getFreshData(lTargetDescriptor);
    }

    @BeforeClass
    public static void init() throws NamingException {
        SimpleNamingContextBuilder lInitialContextBuilder = new SimpleNamingContextBuilder();
        String lEnv = "java:comp/env/";
        String lStub = "NEVERMIND";

        lInitialContextBuilder.bind(lEnv + "application/nbuWebServiceUrl", "https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange");
        lInitialContextBuilder.bind(lEnv + "application/cacheFolderPathWindows", lStub);
        lInitialContextBuilder.bind(lEnv + "application/cacheFolderPathNix", lStub);

        lInitialContextBuilder.activate();
    }

    private TargetDescriptor getTargetDescriptor(int aYear, int aMonth) {
        return getTargetDescriptor("USD", aYear, aMonth);
    }

    private TargetDescriptor getTargetDescriptor(String aCurrency, int aYear, int aMonth) {
        RawTargetDescriptor lRawTargetDescriptor = new RawTargetDescriptor();
        lRawTargetDescriptor.setCurrency(aCurrency);
        lRawTargetDescriptor.setYear(aYear);
        lRawTargetDescriptor.setMonth(aMonth);

        TargetDescriptor lTargetDescriptor = new TargetDescriptor(lRawTargetDescriptor);

        try {
            Class<TargetDescriptor> lMeta = TargetDescriptor.class;
            Field lField = lMeta.getDeclaredField("fIsCurrentMonth");
            lField.setAccessible(true);
            lField.setBoolean(lTargetDescriptor, true);
        } catch (NoSuchFieldException | IllegalAccessException aE) {
            aE.printStackTrace();
            throw new RuntimeException(aE);
        }

        return lTargetDescriptor;
    }
}