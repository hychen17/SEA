package com.sea.template.config;

import com.sea.template.engine.poller.PollerClient;
import com.sea.template.engine.poller.RestPollerClient;
import com.sea.template.engine.repository.JDBCOrderbookRepository;
import com.sea.template.engine.poller.OrderBookPoller;
import com.sea.template.model.SpotDepth;
import com.sea.template.model.OrderBookDataFormat;
import org.slf4j.Logger;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.slf4j.LoggerFactory.getLogger;

@Configuration
@EnableConfigurationProperties({OrderBookDataFormat.class, SpotDepth.class})
public class OderbookPollerConfig {

    private static final Logger log = getLogger(OderbookPollerConfig.class);
    @Bean
    public OrderBookPoller okexOrderBookPoller(PollerClient pollerClient,
                                               JDBCOrderbookRepository orderbookRepository,
                                               ExecutorService executorService,
                                               OrderBookDataFormat orderBookDataFormat){
        return new OrderBookPoller(pollerClient, orderbookRepository, executorService, orderBookDataFormat);
    }

    @Bean
    public RestPollerClient pollerClient(RestTemplateBuilder builder) {
        return new RestPollerClient(builder.build());
    }


    @Bean
    public ExecutorService executorService() {
        return Executors.newFixedThreadPool(orderBookDataFormat().getSpotDepths().size());
    }

    @Bean
    public OrderBookDataFormat orderBookDataFormat() {
        return new OrderBookDataFormat();
    }

    @Bean
    public SpotDepth spotDepth() { return new SpotDepth();}
}
