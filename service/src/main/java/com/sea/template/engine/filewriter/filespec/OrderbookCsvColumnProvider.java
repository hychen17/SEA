package com.sea.template.engine.filewriter.filespec;

import com.sea.template.lang.OrderBookDataFormat;
import com.sea.template.model.SpotDepth;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

import static org.slf4j.LoggerFactory.getLogger;

public class OrderbookCsvColumnProvider {

    private final static Logger log = getLogger(OrderbookCsvColumnProvider.class);


    public BiFunction<OrderBookDataFormat, SpotDepth, List<String>> getColumnProvider() {
        return (orderBookDataFormat, instrument) -> {
            switch (orderBookDataFormat) {
                case SPOT_DEPTH:
                    List<String> headers = buildSpotDepthColumnHeader(Integer.valueOf(instrument.getSize()));
                    log.info("Column Header: {}", String.join(",", headers));
                    return headers;
                default:
                    throw new IllegalArgumentException(String.format("No column spec for trade data format: %s", orderBookDataFormat));
            }
        };
    }

    private List<String> buildSpotDepthColumnHeader(int spotDepthLevel) {
        List<String> columnHeader = new ArrayList<>();
        columnHeader.add("Time");

        for (int i = 1; i <= spotDepthLevel; i++) {
            columnHeader.add(String.format("Bid%sPx", i));
            columnHeader.add(String.format("Bid%sSz", i));
        }

        for (int i = 1; i <= spotDepthLevel; i++) {
            columnHeader.add(String.format("Ask%sPx", i));
            columnHeader.add(String.format("Ask%sSz", i));
        }

        return columnHeader;
    }
}
