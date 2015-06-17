package org.cms.data.app;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import static org.springframework.boot.SpringApplication.run;

@SpringBootApplication
@ComponentScan({"org.cms.data.app.config", "org.cms.data.config"})
public class Application {

    public static void main(String[] args) {
        run(Application.class, args);
    }

}