package com.sea.template.config;

import com.sea.template.Repository.JDBCOrderbookRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@Configuration
public class RepositoryConfig {
    @Bean
    JDBCOrderbookRepository jdbcOrderbookRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        return new JDBCOrderbookRepository(jdbcTemplate);
    }
}
