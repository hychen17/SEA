package com.sea.template.config;

import com.sea.template.Repository.JDBCOrderbookRepository;
import com.sea.template.poller.OkexOrderBookPoller;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.slf4j.LoggerFactory.getLogger;

@Configuration
public class OkexPollerConfig {

    private static final Logger log = getLogger(OkexPollerConfig.class);
    @Bean
    public OkexOrderBookPoller okexOrderBookPoller(RestTemplate restTemplate,
                                                   JDBCOrderbookRepository orderbookRepository,
                                                   ExecutorService executorService,
                                                   @Value("${poller.instruments}") String instruments){
        return new OkexOrderBookPoller(restTemplate, orderbookRepository, executorService, instruments);
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    //TODO: This is to be change to a ScheduledThreadPoolLater
    @Bean
    public ExecutorService executorService() {
        return Executors.newFixedThreadPool(2);
    }
}
