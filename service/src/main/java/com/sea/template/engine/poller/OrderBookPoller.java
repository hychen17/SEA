package com.sea.template.engine.poller;

import com.sea.template.engine.repository.JDBCOrderbookRepository;
import com.sea.template.model.SpotDepth;
import com.sea.template.model.OrderBookDataFormat;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;

import java.util.concurrent.ExecutorService;

import static com.sea.template.lang.RawRecordStatus.NEW;
import static org.slf4j.LoggerFactory.getLogger;

public class OrderBookPoller {

    @Value("${poller.polling.address}")
    private String OKEX_INSTRUMENT_API_URL;

    private PollerClient pollerClient;
    private JDBCOrderbookRepository orderbookRepository;
    private ExecutorService executors;
    private OrderBookDataFormat orderBookDataFormat;

    private final static Logger log = getLogger(OrderBookPoller.class);

    //TODO: Change this to an interface OkexClient, and have rest/websocket implements it.
    // In order to make this class client agnostic.
    public OrderBookPoller(PollerClient pollerClient,
                           JDBCOrderbookRepository orderbookRepository,
                           ExecutorService executors,
                           OrderBookDataFormat orderBookDataFormat) {
        this.pollerClient = pollerClient;
        this.orderbookRepository = orderbookRepository;
        this.executors = executors;
        this.orderBookDataFormat = orderBookDataFormat;
    }

    public void pollOrderBook() {
        for (SpotDepth spotDepth : orderBookDataFormat.getSpotDepths()) {
            log.info("Requesting <{}>, size=<{}>, depth=<{}>", spotDepth.getInstrument(), spotDepth.getSize(), spotDepth.getDepth());
            executors.submit(() -> {
                StringBuilder sb = new StringBuilder();
                sb.append(OKEX_INSTRUMENT_API_URL);
                sb.append(spotDepth.getInstrument());
                sb.append("/book?size=");
                sb.append(spotDepth.getSize());
                sb.append("&depth=");
                sb.append(spotDepth.getDepth());

                log.info("Requesting: {}", sb.toString());
                String record = (String) pollerClient.poll(sb.toString());
                // Persist the raw record to db.
                log.info("Received: {}", record);
                orderbookRepository.persistRecord(record, spotDepth.getInstrument(), NEW.name());
            });
        }
    }


}
