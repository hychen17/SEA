package com.sea.template.engine;

import com.sea.template.engine.filewriter.OrderbookDataHandler;
import com.sea.template.engine.poller.OrderBookPoller;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static org.slf4j.LoggerFactory.getLogger;

public class OrderbookDataTaskScheduler {

    @Value("${poller.polling.rate}")
    private int pollingRate;
    @Value("${task-scheduler.write-csv.rate}")
    private int toCsvRate;

    private ScheduledExecutorService pollerService;
    private ScheduledExecutorService dataHandlerService;
    private OrderbookDataHandler orderbookDataHandler;
    private OrderBookPoller orderBookPoller;

    private static final Logger log = getLogger(OrderbookDataTaskScheduler.class);

    public OrderbookDataTaskScheduler(ScheduledExecutorService pollerService,
                                      ScheduledExecutorService dataHandlerService,
                                      OrderbookDataHandler orderbookDataHandler,
                                      OrderBookPoller orderBookPoller) {
        this.pollerService = pollerService;
        this.dataHandlerService = dataHandlerService;
        this.orderbookDataHandler = orderbookDataHandler;
        this.orderBookPoller = orderBookPoller;
    }

    public void exportToCsvTask() {
        log.info("Start to export order book raw data to csv every {} ms...", toCsvRate);
        dataHandlerService.scheduleAtFixedRate(() -> orderbookDataHandler.saveRepoDataToCsv(), 0, pollingRate, TimeUnit.MILLISECONDS);
    }

    public void pollOrderbookDataTask() {
        log.info("Start to poll book raw data from Okex every {} ms...", pollingRate);
        pollerService.scheduleAtFixedRate(() -> orderBookPoller.pollOrderBook(), 0, toCsvRate, TimeUnit.MILLISECONDS);
    }

    public void stopPolling() throws InterruptedException {
        pollerService.shutdown();
        while (!pollerService.awaitTermination(3, TimeUnit.SECONDS)) {
            log.info("Stoping polling...");
        }
        log.info("Polling stopped.");
    }

    public void stopHandlingPulledData() throws InterruptedException {
        dataHandlerService.shutdown();
        while (!dataHandlerService.awaitTermination(10, TimeUnit.SECONDS)) {
            log.info("Stoping handling pulled data...");
        }
        log.info("Handling pulled data stopped.");
    }
}
