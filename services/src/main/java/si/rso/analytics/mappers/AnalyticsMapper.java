package si.rso.analytics.mappers;

import si.rso.analytics.lib.Analytics;
import si.rso.analytics.persistence.AnalyticsEntity;

public class AnalyticsMapper {
    
    public static Analytics fromEntity(AnalyticsEntity entity) {
        Analytics analytics = new Analytics();
        analytics.setId(entity.getId());
        analytics.setTimestamp(entity.getTimestamp());
        analytics.setProductId(entity.getProductId());
        analytics.setNumberOfOrders(entity.getNumberOfOrders());
        analytics.setIncome(entity.getIncome());
        return analytics;
    }
    
}