package com.sea.template.api;

import com.sea.template.poller.OkexOrderBookPoller;
import com.sea.template.websocket.OkWebSocket;
import com.sea.template.websocket.OkWebSocketClient;
import org.java_websocket.WebSocket;
import org.slf4j.Logger;
import org.springframework.web.client.RestTemplate;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.concurrent.ExecutionException;

import static org.slf4j.LoggerFactory.getLogger;

@Path("/ok")
public class OkWebSocketClientApi {

    private static final Logger log = getLogger(OkWebSocketClientApi.class);
    OkWebSocketClient okWebSocketClient;
    OkWebSocket okWebSocket;
    OkexOrderBookPoller poller;


    public OkWebSocketClientApi(OkWebSocketClient okWebSocketClient,
                                OkWebSocket okWebSocket,
                                OkexOrderBookPoller poller) {
        this.okWebSocketClient = okWebSocketClient;
        this.okWebSocket = okWebSocket;
        this.poller = poller;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/invoke")
    public Response getManager() throws ExecutionException, InterruptedException {
//        okWebSocketClient.initWebSocketClient();
//        okWebSocket.connect();
//        while (!okWebSocket.getReadyState().equals(WebSocket.READYSTATE.OPEN)) {
//            System.out.println("还没有打开");
//        }
//        System.out.println("建立websocket连接");
        poller.pollOrderBook();
        return Response.ok("ok").build();
    }
}
