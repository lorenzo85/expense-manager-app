package org.cms.rest;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import static org.springframework.boot.SpringApplication.run;

@SpringBootApplication
@ComponentScan({"org.cms.rest.config", "org.cms.core"})
public class Application {

    public static void main(String[] args) {
        run(Application.class, args);
    }

}