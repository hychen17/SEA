package config;

import api.JaxrsApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@Configuration
@Import({TemplateApiConfig.class})
public class ServiceConfig {

    @Bean
    JaxrsApplication restApplication() {
        return new JaxrsApplication();
    }

}
