package com.sea.template.config;

import com.sea.template.engine.filewriter.OrderbookCsvFileManager;
import com.sea.template.engine.filewriter.OrderbookDataHandler;
import com.sea.template.engine.repository.JDBCOrderbookRecordActionRepository;
import com.sea.template.engine.repository.JDBCOrderbookRepository;
import com.sea.template.model.OrderBookDataFormat;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class OrderbookDataHandlerConfig {

    @Bean
    public OrderbookDataHandler orderbookDataHandler(JDBCOrderbookRepository orderbookRepo,
                                                     JDBCOrderbookRecordActionRepository orderbookCsvRepo,
                                                     OrderbookCsvFileManager csvFileManager,
                                                     ExecutorService handlerExecutorService,
                                                     OrderBookDataFormat orderBookDataFormat) {
        return new OrderbookDataHandler(orderbookRepo,
                orderbookCsvRepo,
                csvFileManager,
                handlerExecutorService,
                orderBookDataFormat);
    }

    // Each Intrument will be using a dedicate thread to write data to csv file (i.e. 2 threads for BTC-USDT, ETH-USDT)
    @Bean
    public ExecutorService handlerExecutorService(OrderBookDataFormat orderBookDataFormat) {
        return Executors.newFixedThreadPool(orderBookDataFormat.getSpotDepths().size());
    }

}
