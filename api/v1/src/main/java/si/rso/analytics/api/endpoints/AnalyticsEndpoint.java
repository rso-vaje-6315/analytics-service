package si.rso.analytics.api.endpoints;

import si.rso.analytics.lib.Analytics;
import si.rso.analytics.services.AnalyticsService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/analytics")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AnalyticsEndpoint {
    
    @Inject
    private AnalyticsService analyticsService;

    @GET
    @Path("/{productId}")
    public Response getGreetings(@PathParam("productId") String productId) {
        Analytics analytics = analyticsService.getProductAnalytics(productId);
        return Response.ok(analytics).build();
    }

}
