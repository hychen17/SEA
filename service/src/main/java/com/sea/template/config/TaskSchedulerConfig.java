package com.sea.template.config;

import com.sea.template.engine.OrderbookDataTaskScheduler;
import com.sea.template.engine.filewriter.OrderbookDataHandler;
import com.sea.template.engine.poller.OrderBookPoller;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Configuration
public class TaskSchedulerConfig {

    @Bean
    public OrderbookDataTaskScheduler orderbookDataTaskScheduler(ScheduledExecutorService pollerService,
                                                                 ScheduledExecutorService dataHandlerService,
                                                                 OrderbookDataHandler orderbookDataHandler,
                                                                 OrderBookPoller orderBookPoller) {
        return new OrderbookDataTaskScheduler(pollerService, dataHandlerService, orderbookDataHandler, orderBookPoller);
    }
    @Bean
    public ScheduledExecutorService pollerService() {
        return Executors.newSingleThreadScheduledExecutor();
    }

    @Bean
    public ScheduledExecutorService dataHandlerService() {
        return Executors.newScheduledThreadPool(2);
    }
}
