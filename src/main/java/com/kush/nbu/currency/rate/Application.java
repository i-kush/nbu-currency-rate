package com.kush.nbu.currency.rate;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class Application extends SpringBootServletInitializer {

    public static void main(String[] aArgs) {
        SpringApplication lSpringApplication = new SpringApplication(Application.class);
        lSpringApplication.setBannerMode(Banner.Mode.OFF);
        lSpringApplication.run(aArgs);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder aBuilder) {
        return aBuilder.sources(Application.class);
    }
}