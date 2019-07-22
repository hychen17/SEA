package com.sea.template.config;

import com.sea.template.api.TemplateApi;
import com.sea.template.guest.JDBCGuestRepo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@Configuration
@Import({GuestConfig.class})
public class TemplateApiConfig {

    @Bean
    public TemplateApi templateApi(JDBCGuestRepo repo) {
        return new TemplateApi(repo);
    }
}
