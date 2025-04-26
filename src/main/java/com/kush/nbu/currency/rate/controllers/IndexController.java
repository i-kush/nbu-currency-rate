package com.kush.nbu.currency.rate.controllers;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    private final static String BASE_HREF_TO_REPLACE = "<base href=\"/\">";
    private final static String BASE_HREF_TO_REPLACE_WITH = "<base href=\"%s/\">";

    private String fPageContentIndex;

    @GetMapping(value = {"/index.html", "/monthly-rate", "/monthly-rate/**"})
    public String getIndex(HttpServletRequest aRequest) {
        if (fPageContentIndex == null) {
            try {
                fPageContentIndex = getIndexFileContent(aRequest);
            } catch (IOException aE) {
                aE.printStackTrace();
            }
        }

        return fPageContentIndex;
    }

    private String getIndexFileContent(HttpServletRequest aRequest) throws IOException {
        InputStream lInputStream = aRequest.getServletContext().getResourceAsStream("/index.html");
        String lFileContent = getStreamContents(lInputStream);
        String lBase = String.format(BASE_HREF_TO_REPLACE_WITH, aRequest.getContextPath());

        return lFileContent.replaceAll(BASE_HREF_TO_REPLACE, lBase);
    }

    private String getStreamContents(InputStream aStream) throws IOException {
        try (BufferedReader lReader = new BufferedReader(new InputStreamReader(aStream))) {
            StringBuilder lResult = new StringBuilder();

            String lLine;
            while ((lLine = lReader.readLine()) != null) {
                lResult.append(lLine).append(System.lineSeparator());
            }

            return lResult.toString();
        }
    }
}
