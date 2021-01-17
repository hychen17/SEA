package com.sea.template.engine.poller;

import org.springframework.web.socket.messaging.WebSocketStompClient;

public class WebsocketPollerClient implements PollerClient<WebSocketStompClient, String> {
    private WebSocketStompClient delegate;

    public WebsocketPollerClient(WebSocketStompClient webSocketClient) {
        this.delegate = webSocketClient;
    }

    public String poll(String url) {
        return "Not Implemented yet";
    }

    public WebSocketStompClient delegate() {
        return this.delegate;
    }
}
