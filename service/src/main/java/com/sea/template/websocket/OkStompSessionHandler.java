package com.sea.template.websocket;

import org.slf4j.Logger;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;

import static org.slf4j.LoggerFactory.getLogger;

public class OkStompSessionHandler extends StompSessionHandlerAdapter {
    private static final Logger log = getLogger(OkStompSessionHandler.class);

    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        log.info("********** Ready to subscribe **********");
        session.subscribe("wss://real.okex.com:8443/ws/v3", this);
        log.info("********** Sending {} **********");
        String request = "{\"op\": \"subscribe\", \"args\": [\"spot/depth:ETH-USDT\"]}";
        session.send("wss://real.okex.com:8443/ws/v3", request);
        log.info("********** Sent **********");
    }
    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        log.info("Received : " + payload.toString());
    }
}
