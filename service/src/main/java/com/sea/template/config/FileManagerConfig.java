package com.sea.template.config;

import com.sea.template.engine.filewriter.OrderbookCsvFileManager;
import com.sea.template.engine.filewriter.filespec.OrderbookCsvColumnProvider;
import com.sea.template.engine.repository.JDBCOrderbookRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FileManagerConfig {

    @Bean
    public OrderbookCsvFileManager orderbookCsvFileManager(OrderbookCsvColumnProvider orderbookCsvColumnProvider,
                                                           JDBCOrderbookRepository orderbookRepository) {
        return new OrderbookCsvFileManager(orderbookCsvColumnProvider.getColumnProvider(), orderbookRepository);
    }

    @Bean
    public OrderbookCsvColumnProvider orderbookCsvColumnProvider() {
        return new OrderbookCsvColumnProvider();
    }

}
