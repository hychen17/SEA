package com.sea.template.lang;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Map;

import static com.google.common.collect.Maps.asMap;

public class JsonParser {

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static Map<String, Object> fromJson2Map(String json) throws IOException {
        return asMap(objectMapper.readTree(json));

    }

    public static Map<String, Object> asMap(JsonNode node) {
        return objectMapper.convertValue(node, Map.class);
    }
}
