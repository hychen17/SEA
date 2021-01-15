package com.sea.template.config;

import com.sea.template.websocket.OkStompSessionHandler;
import com.sea.template.websocket.OkWebSocket;
import com.sea.template.websocket.OkWebSocketClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.stomp.StompSessionHandler;

import java.net.URISyntaxException;

@Configuration
public class OkWebSocketConfig {

    @Bean
    public OkWebSocketClient okWebSocketClient() {
        OkWebSocketClient okWebSocketClient = new OkWebSocketClient(new OkStompSessionHandler());
        return okWebSocketClient;
    }

    @Bean
    public OkWebSocket okWebSocket() throws URISyntaxException {
        return new OkWebSocket("wss://real.okex.com:8443/ws/v3");
    }
}
