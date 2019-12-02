package si.rso.analytics.services;

import si.rso.analytics.lib.Analytics;

public interface AnalyticsService {
    
    Analytics getProductAnalytics(String productId);

}