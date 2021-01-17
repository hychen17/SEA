package com.sea.template.engine.api;

import com.sea.template.engine.OrderbookDataTaskScheduler;
import com.sea.template.engine.filewriter.OrderbookDataHandler;
import com.sea.template.engine.poller.OrderBookPoller;
import org.slf4j.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.slf4j.LoggerFactory.getLogger;

@Path("/ok")
public class ServiceApi {

    private static final Logger log = getLogger(ServiceApi.class);
    OrderBookPoller poller;
    OrderbookDataHandler orderbookDataHandler;
    OrderbookDataTaskScheduler orderbookDataTaskScheduler;


    public ServiceApi(OrderBookPoller poller,
                      OrderbookDataHandler orderbookDataHandler,
                      OrderbookDataTaskScheduler orderbookDataTaskScheduler) {
        this.poller = poller;
        this.orderbookDataHandler = orderbookDataHandler;
        this.orderbookDataTaskScheduler = orderbookDataTaskScheduler;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/order-books")
    public Response pollRecords(@QueryParam("op") String operation) throws InterruptedException {
        switch(operation) {
            // Below four are scheduled iterative tasks
            case "poll":
                orderbookDataTaskScheduler.pollOrderbookDataTask();
                return Response.ok(Response.Status.OK.toString()).build();
            case "tocsv":
                orderbookDataTaskScheduler.exportToCsvTask();
                return Response.ok(Response.Status.OK.toString()).build();
            case "stop-poll":
                orderbookDataTaskScheduler.stopPolling();
                return Response.ok("Polling stopped").build();
            case "stop-tocsv":
                orderbookDataTaskScheduler.stopHandlingPulledData();
                return Response.ok("Handling pulled data stopped.").build();

            //Below two are for debugging single operation...
            case "single-poll":
                poller.pollOrderBook();
                return Response.ok("polled").build();
            case "single-touch":
                orderbookDataHandler.saveRepoDataToCsv();
                return Response.ok("touched").build();
            default:
                return Response.ok(Response.Status.BAD_REQUEST.toString()).build();
        }
    }

}
