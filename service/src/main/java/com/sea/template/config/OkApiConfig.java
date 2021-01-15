package com.sea.template.config;

import com.sea.template.api.OkWebSocketClientApi;
import com.sea.template.poller.OkexOrderBookPoller;
import com.sea.template.websocket.OkWebSocket;
import com.sea.template.websocket.OkWebSocketClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({OkWebSocketConfig.class})
public class OkApiConfig {

    @Bean
    public OkWebSocketClientApi okWebSocketClientApi(OkWebSocketClient okWebSocketClient,
                                                     OkWebSocket okWebSocket,
                                                     OkexOrderBookPoller poller) {
        return new OkWebSocketClientApi(okWebSocketClient, okWebSocket, poller);
    }



}
