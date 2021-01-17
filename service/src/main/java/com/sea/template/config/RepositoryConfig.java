package com.sea.template.config;

import com.sea.template.engine.repository.JDBCOrderbookRecordActionRepository;
import com.sea.template.engine.repository.JDBCOrderbookRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@Configuration
public class RepositoryConfig {
    @Bean
    JDBCOrderbookRepository jdbcOrderbookRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        return new JDBCOrderbookRepository(jdbcTemplate);
    }

    @Bean
    JDBCOrderbookRecordActionRepository orderbookCsvRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        return new JDBCOrderbookRecordActionRepository(jdbcTemplate);
    }
}
