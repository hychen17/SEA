package com.sea.template.lang.security;

import org.slf4j.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


import static org.slf4j.LoggerFactory.getLogger;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final Logger log = getLogger(SecurityConfig.class);

    private static final String API_URLs = "/api/**";

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers(API_URLs).authenticated()
                .and()
                .httpBasic();
    }

}
