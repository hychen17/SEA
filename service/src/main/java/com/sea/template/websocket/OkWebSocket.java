package com.sea.template.websocket;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.collect.ImmutableList;
import org.java_websocket.WebSocket;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.slf4j.Logger;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Iterator;

import static org.slf4j.LoggerFactory.getLogger;

public class OkWebSocket extends WebSocketClient {

        private static final Logger log = getLogger(OkWebSocket.class);
        public OkWebSocket(String url) throws URISyntaxException {
            super(new URI(url));
        }

        @Override
        public void onOpen(ServerHandshake shake) {
            System.out.println("握手...");
            for(Iterator<String> it = shake.iterateHttpFields(); it.hasNext();) {
                String key = it.next();
                System.out.println(key+":"+shake.getFieldValue(key));
            }
            ObjectMapper mapper = new ObjectMapper();

            // create a JSON object
            ObjectNode req = mapper.createObjectNode();
            req.put("op", "subscribe");
            req.put("args", "[\"spot/depth:ETH-USDT\"]");

            String request = null;
                request = "{\"op\":\"subscribe\", \"args\":[\"spot/depth:ETH-USDT\"]}";
                log.info("********** Sending {} **********", request);
                this.send(request);
        }

        @Override
        public void onMessage(String paramString) {
            System.out.println("接收到消息："+paramString);
        }

        @Override
        public void onClose(int paramInt, String paramString, boolean paramBoolean) {
            System.out.println("关闭...");
        }

        @Override
        public void onError(Exception e) {
            System.out.println("异常"+e);

        }
}
