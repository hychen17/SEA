package com.sea.template.config;

import com.sea.template.engine.OrderbookDataTaskScheduler;
import com.sea.template.engine.filewriter.OrderbookDataHandler;
import com.sea.template.engine.api.ServiceApi;
import com.sea.template.engine.poller.OrderBookPoller;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceApiConfig {

    @Bean
    public ServiceApi serviceApi(OrderBookPoller poller,
                                 OrderbookDataHandler orderbookDataHandler,
                                 OrderbookDataTaskScheduler orderbookDataTaskScheduler) {
        return new ServiceApi(
                poller,
                orderbookDataHandler,
                orderbookDataTaskScheduler);
    }



}
