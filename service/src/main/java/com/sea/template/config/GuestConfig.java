package com.sea.template.config;

import com.sea.template.guest.JDBCGuestRepo;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

public class GuestConfig {

    @Bean
    JDBCGuestRepo jdbcGuestRepo(NamedParameterJdbcTemplate jdbcTemplate) {
        return new JDBCGuestRepo(jdbcTemplate);
    }
}
