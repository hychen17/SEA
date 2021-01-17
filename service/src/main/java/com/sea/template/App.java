package com.sea.template;

import com.sea.template.config.ServiceConfig;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

import static org.springframework.boot.SpringApplication.*;


@SpringBootApplication
@EnableConfigurationProperties
@Import({ServiceConfig.class})
public class App {
    public static void main(String[] args) {
        run(App.class, args);
    }
}
