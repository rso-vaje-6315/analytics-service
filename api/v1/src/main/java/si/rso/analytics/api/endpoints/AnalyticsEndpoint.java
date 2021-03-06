package si.rso.analytics.api.endpoints;

import com.kumuluz.ee.logs.LogManager;
import com.kumuluz.ee.logs.Logger;
import com.kumuluz.ee.logs.cdi.Log;
import com.kumuluz.ee.streaming.common.annotations.StreamListener;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.eclipse.microprofile.metrics.annotation.Counted;
import si.rso.analytics.lib.Analytics;
import si.rso.analytics.lib.AnalyticsStreamConfig;
import si.rso.analytics.services.AnalyticsService;
import si.rso.event.streaming.EventStreamMessage;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Log
@Path("/analytics")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AnalyticsEndpoint {
    private static final Logger LOG = LogManager.getLogger(AnalyticsEndpoint.class.getSimpleName());
    
    @Inject
    private AnalyticsService analyticsService;

    @GET
    @Path("/{productId}")
    @Counted(name = "get-analytics-count")
    public Response getAnalytics(@PathParam("productId") String productId) {
        Analytics analytics = analyticsService.getProductAnalytics(productId);
        return Response.ok(analytics).build();
    }

    @Counted(name = "kafka-number-of-events")
    @StreamListener(topics = {AnalyticsStreamConfig.NOTIFICATIONS_CHANNEL})
    public void onMessage(ConsumerRecord<String, EventStreamMessage> record) {
        LOG.info("message consumed");
        analyticsService.handleMessage(record.value());
    }
}
