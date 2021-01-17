package com.sea.template.engine.filewriter;

import com.sea.template.engine.repository.JDBCOrderbookRecordActionRepository;
import com.sea.template.engine.repository.JDBCOrderbookRepository;
import com.sea.template.model.BidAsk;
import com.sea.template.model.SpotDepth;
import com.sea.template.model.OrderBookDataFormat;
import com.sea.template.model.SpotDepthRecord;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.slf4j.Logger;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutorService;

import static com.sea.template.lang.OrderBookDataAction.TO_CSV;
import static java.util.stream.Collectors.toList;
import static org.slf4j.LoggerFactory.getLogger;

public class OrderbookDataHandler {

    private JDBCOrderbookRepository orderbookRepository;
    private JDBCOrderbookRecordActionRepository orderbookCsvRepository;
    private OrderbookCsvFileManager csvFileManager;
    private ExecutorService executorService;
    private OrderBookDataFormat orderBookDataFormat;

    private final static Logger log = getLogger(OrderbookDataHandler.class);


    public OrderbookDataHandler(JDBCOrderbookRepository orderbookRepository,
                                JDBCOrderbookRecordActionRepository orderbookCsvRepository,
                                OrderbookCsvFileManager csvFileManager,
                                ExecutorService executorService,
                                OrderBookDataFormat orderBookDataFormat) {
        this.orderbookRepository = orderbookRepository;
        this.orderbookCsvRepository = orderbookCsvRepository;
        this.csvFileManager = csvFileManager;
        this.executorService = executorService;
        this.orderBookDataFormat = orderBookDataFormat;
    }

    public void saveRepoDataToCsv(){
        for (SpotDepth spotDepth : orderBookDataFormat.getSpotDepths()) {
            executorService.submit(() -> {
                List<SpotDepthRecord> records = orderbookRepository.getRecordsByInstruments(spotDepth.getInstrument());

                //If the record cannot been persist to JDBCOrderbookRecordActionRepository,
                // that means this chunk of records are probably handled by other thread
                try {
                    List<Pair<UUID, List<String>>> csvDataRows = toCsvDataRow(records);
                    if (!csvDataRows.isEmpty()) {
                        csvFileManager.appendSpotDepthFile(spotDepth, csvDataRows);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            });
        }
    }

    private List<Pair<UUID, List<String>>> toCsvDataRow(List<SpotDepthRecord> records) {
        return records.stream()
                .filter(Objects::nonNull)
                .filter(r -> notWritternToCsv(r.getId()))
                .map(record -> {
                    List<String> dataRow = new ArrayList<>();
                    dataRow.add(record.getTimestamp().toString());
                    for (BidAsk bid : record.getBids()) {
                        dataRow.add(bid.getPrice().toPlainString());
                        dataRow.add(bid.getSize().toPlainString());
                    }

                    for (BidAsk ask : record.getAsks()) {
                        dataRow.add(ask.getPrice().toPlainString());
                        dataRow.add(ask.getSize().toPlainString());
                    }
                    return ImmutablePair.of(UUID.fromString(record.getId()),dataRow);
                })
                .collect(toList());
    }

    private boolean notWritternToCsv(String id) {
        try {
            orderbookCsvRepository.persistRecord(id, TO_CSV.name(), DateTime.now(DateTimeZone.UTC).toString());
            return true;
        } catch (Exception e) {
            log.warn("<{}> probably being export to csv.", id);
            return false;
        }
    }
}
