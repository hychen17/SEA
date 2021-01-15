package com.sea.template.websocket;

import org.slf4j.Logger;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.util.Scanner;
import java.util.concurrent.ExecutionException;

import static org.slf4j.LoggerFactory.getLogger;

public class OkWebSocketClient {
    private static final Logger log = getLogger(OkWebSocketClient.class);
    private StompSessionHandler stompSessionHandler;

    public OkWebSocketClient(StompSessionHandler stompSessionHandler) {
        this.stompSessionHandler = stompSessionHandler;
    }

    public void initWebSocketClient() throws ExecutionException, InterruptedException {
        WebSocketClient client = new StandardWebSocketClient();

        WebSocketStompClient stompClient = new WebSocketStompClient(client);
        log.info("@@@@@@@@@@@ ready to connect");
        ListenableFuture<StompSession> stompSession = stompClient.connect("wss://real.okex.com:8443/ws/v3", stompSessionHandler);
        new Scanner(System.in).nextLine();

    }
}
