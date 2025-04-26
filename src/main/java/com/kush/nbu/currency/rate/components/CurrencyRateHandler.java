package com.kush.nbu.currency.rate.components;

import com.kush.nbu.currency.rate.configs.EnvEntries;
import com.kush.nbu.currency.rate.dto.http.TargetDescriptor;
import com.kush.nbu.currency.rate.dto.xml.CurrencyHolder;
import com.kush.nbu.currency.rate.exceptions.AverageRateRequestException;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

@Component
public class CurrencyRateHandler {

    private final String fCacheDirLocation;

    public CurrencyRateHandler() {
        if (System.getProperty("os.name").toLowerCase().contains("win")) {
            fCacheDirLocation = EnvEntries.getString("application/cacheFolderPathWindows");
        } else {
            fCacheDirLocation = EnvEntries.getString("application/cacheFolderPathNix");
        }
    }

    public double calculateAverageCurrencyRate(TargetDescriptor aTargetDescriptor) throws IOException {
        double lResult;

        String lCashedValue = extractFromCashRawValue(aTargetDescriptor);
        if (lCashedValue != null && !aTargetDescriptor.isCurrentMonth()) {
            lResult = Double.valueOf(lCashedValue);
        } else {
            System.out.println("getting fresh data");
            lResult = getFreshData(aTargetDescriptor);
        }

        return lResult;
    }

    private String extractFromCashRawValue(TargetDescriptor aTargetDescriptor) throws IOException {
        String lResult = null;

        File lFile = getPath(aTargetDescriptor);
        if (lFile.exists()) {
            lResult = new String(Files.readAllBytes(lFile.toPath()), StandardCharsets.UTF_8);
        }

        return lResult;
    }

    private File getPath(TargetDescriptor aTargetDescriptor) {
        String lPath = String.format("%s/%s/%s.txt",
                aTargetDescriptor.getYear(),
                aTargetDescriptor.getMonth(),
                aTargetDescriptor.getCurrency()
        );

        return new File(fCacheDirLocation, lPath);
    }

    public double getFreshData(TargetDescriptor aTargetDescriptor) throws IOException {
        double lResultRaw = 0.0;
        String lNbuWebServiceUrl = EnvEntries.getString("application/nbuWebServiceUrl");
        int lDay = 1;
        while (true) {
            String lUrl = String.format(
                    "%s?date=%s%s%s",
                    lNbuWebServiceUrl,
                    aTargetDescriptor.getYear(),
                    aTargetDescriptor.getMonth(),
                    aTargetDescriptor.convertMonth(lDay)
            );
            CurrencyHolder lCurrencyHolder = loadCurrencyRatesInfo(lUrl);
            if (lCurrencyHolder == null) {
                break;
            }
            CurrencyHolder.Currency lCurrency = lCurrencyHolder.find(aTargetDescriptor.getCurrency());
            if (lCurrency == null) {
                break;
            }
            lResultRaw += lCurrency.getRate();
            lDay++;
        }

        double lResult = lResultRaw / (lDay - 1);

        if (Double.isNaN(lResult)) {
            throw new AverageRateRequestException("No data available for " + aTargetDescriptor);
        }

        if (!aTargetDescriptor.isCurrentMonth()) {
            System.out.println("caching");
            cache(aTargetDescriptor, lResult);
        }

        return lResult;
    }

    private CurrencyHolder loadCurrencyRatesInfo(String aUrl) {
        CurrencyHolder lResult;

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(CurrencyHolder.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            lResult = (CurrencyHolder) jaxbUnmarshaller.unmarshal(new URL(aUrl));
        } catch (Exception aE) {
            lResult = null;
        }

        return lResult;
    }

    private void cache(TargetDescriptor aTargetDescriptor, double lResult) throws IOException {
        File lFile = getPath(aTargetDescriptor);
        Files.createDirectories(Paths.get(lFile.getParent()));
        Files.write(lFile.toPath(), String.valueOf(lResult).getBytes());
    }
}
