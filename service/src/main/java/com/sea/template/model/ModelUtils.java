package com.sea.template.model;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ModelUtils {

    private static final int BIDASK_PRICE_INDEX = 0;
    private static final int BIDASK_SIZE_INDEX = 1;
    private static final int BIDASK_NUM_OF_ORD_INDEX = 2;

    //TODO: Handle the corrupted field
    public static SpotDepthRawRecord fromMap(Map<String, Object> rawRecordMap) {
        List<BidAsk> asks = ((List<List<String>>)rawRecordMap.get("asks")).stream()
                .map(ModelUtils::fromTripple)
                .collect(Collectors.toList());

        List<BidAsk> bids = ((List<List<String>>)rawRecordMap.get("bids")).stream()
                .map(ModelUtils::fromTripple)
                .collect(Collectors.toList());

        return SpotDepthRawRecord.builder()
                .asks(asks)
                .bids(bids)
                .timestamp((String)rawRecordMap.get("timestamp"))
                .build();
    }

    public static BidAsk fromTripple(List<String> tripple) {
        return BidAsk.builder()
                .price(tripple.get(BIDASK_PRICE_INDEX))
                .size(tripple.get(BIDASK_SIZE_INDEX))
                .numOfOrders(tripple.get(BIDASK_NUM_OF_ORD_INDEX))
                .build();
    }
}
