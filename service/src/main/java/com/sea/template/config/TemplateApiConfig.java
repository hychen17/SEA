package com.sea.template.config;

import com.sea.template.api.TemplateApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TemplateApiConfig {

    @Bean
    public TemplateApi templateApi() {
        return new TemplateApi();
    }
}
