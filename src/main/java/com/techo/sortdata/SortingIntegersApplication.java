package com.techo.sortdata;


import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class SortingIntegersApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(SortingIntegersApplication.class)
                .web(true)
                .build()
                .run(args);
    }
}
