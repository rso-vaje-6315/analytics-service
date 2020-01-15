package si.rso.analytics.services;

import si.rso.analytics.lib.Analytics;
import si.rso.event.streaming.EventStreamMessage;

public interface AnalyticsService {
    
    Analytics getProductAnalytics(String productId);

    void handleMessage(EventStreamMessage message);

}