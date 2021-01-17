package com.sea.template.engine.filewriter;

import com.opencsv.CSVWriter;
import com.sea.template.engine.repository.JDBCOrderbookRepository;
import com.sea.template.lang.OrderBookDataFormat;
import com.sea.template.model.SpotDepth;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.function.BiFunction;

import static com.sea.template.lang.OrderBookDataFormat.SPOT_DEPTH;
import static com.sea.template.lang.RawRecordStatus.SAVED_TO_CSV;
import static org.slf4j.LoggerFactory.getLogger;

public class OrderbookCsvFileManager {

    private BiFunction<OrderBookDataFormat, SpotDepth, List<String>> columnProvider;
    private JDBCOrderbookRepository orderbookRepository;

    @Value("${csv.order-book.address}")
    private String FILE_ADDRESS;

    private final static String CONTENT_FROM = "order_book";

    private final static String FILE_SUFFIX = ".csv";

    private final static Logger log = getLogger(OrderbookCsvFileManager.class);

    public OrderbookCsvFileManager(BiFunction<OrderBookDataFormat, SpotDepth, List<String>> columnProvider,
                                   JDBCOrderbookRepository orderbookRepository) {
        this.columnProvider = columnProvider;
        this.orderbookRepository = orderbookRepository;
    }

    public void appendSpotDepthFile(SpotDepth spotDepth, List<Pair<UUID, List<String>>> dataRow) throws IOException {

        String fullFileName = String.format("%s%s_%s_%s%s",
                FILE_ADDRESS,
                CONTENT_FROM,
                SPOT_DEPTH.name(),
                spotDepth.getInstrument(),
                FILE_SUFFIX);

        // if file not exist, create it first
        if(!(new File(fullFileName)).exists()) {
            createFile(fullFileName, SPOT_DEPTH, spotDepth);
        }

        //append to file
        appendToFile(fullFileName, dataRow);

    }

    protected void createFile(String fileName,
                           OrderBookDataFormat orderBookDataFormat,
                           SpotDepth spotDepth) throws IOException {

        CSVWriter writer = new CSVWriter(new FileWriter(fileName));

        log.info("Creating file <{}> ...", fileName);
        try (writer) {
            List<String> headers = columnProvider.apply(orderBookDataFormat, spotDepth);
            createColumnHeader(writer, headers);
        }
        log.info("Created file <{}>", fileName);
    }

    private void appendToFile(String fullFileName, List<Pair<UUID, List<String>>> dataRow) throws IOException {
        CSVWriter writer = new CSVWriter(new FileWriter(fullFileName, true));
        try (writer) {
            for (Pair<UUID, List<String>> row : dataRow) {
                String rowId = row.getLeft().toString();
                List<String> rowData = row.getRight();

                writer.writeNext(rowData.toArray(new String[rowData.size()]));
                log.info("Appended <{}>", String.join(",", rowData));

                // Update the status of the raw record to TO_CSV after writing to file
                orderbookRepository.updateRecord(rowId, SAVED_TO_CSV.name());

            }
        }
    }

    protected void createColumnHeader(CSVWriter writer, List<String> columnHeader) {
        writer.writeNext(columnHeader.toArray(new String[columnHeader.size()]));
    }
}
