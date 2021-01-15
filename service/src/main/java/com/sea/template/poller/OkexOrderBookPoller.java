package com.sea.template.poller;

import com.google.common.collect.ImmutableList;
import com.sea.template.Repository.JDBCOrderbookRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import static com.sea.template.lang.RawRecordStatus.NEW;
import static org.slf4j.LoggerFactory.getLogger;

public class OkexOrderBookPoller {

    private RestTemplate restTemplate;
    private JDBCOrderbookRepository orderbookRepository;
    private ExecutorService executors;
    private List<String> instruments;

    private final static String OKEX_INSTRUMENT_API_URL = "https://www.okex.com/api/spot/v3/instruments/";
    private final static Logger log = getLogger(OkexOrderBookPoller.class);

    //TODO: Change this to an interface OkexClient, and have rest/websocket implements it.
    // In order to make this class client agnostic. Same for OrderbookRepository but maybe later.
    public OkexOrderBookPoller(RestTemplate restTemplate,
                               JDBCOrderbookRepository orderbookRepository,
                               ExecutorService executors,
                               String instruments) {
        this.restTemplate = restTemplate;
        this.orderbookRepository = orderbookRepository;
        this.executors = executors;
        this.instruments = Arrays.asList(instruments.split(","));
    }

    public void pollOrderBook() {
        log.info("All the instruments to request for: {}", instruments);
        for (String instrument : instruments) {
            Future<String> rs = executors.submit(() -> {
                StringBuilder sb = new StringBuilder();
                sb.append(OKEX_INSTRUMENT_API_URL);
                sb.append(instrument);
                
                log.info("Requesting: {}", sb.toString());
                //TODO: The problem here is, will this thread get hold of the restTemplate and blocking other thread?
                // can we have something like "restTemplatePool"?
                return restTemplate.getForObject(sb.toString(), String.class);
            });

            executors.submit(() -> {
                try {
                    String record = rs.get();
                    log.info("Received: {}", record);
                    orderbookRepository.persistRecord(record, NEW.name());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            });
        }
    }


}
