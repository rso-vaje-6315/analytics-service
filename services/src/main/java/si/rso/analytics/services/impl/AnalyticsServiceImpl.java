package si.rso.analytics.services.impl;

import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;
import si.rso.analytics.persistence.AnalyticsEntity;
import si.rso.analytics.services.AnalyticsService;
import si.rso.analytics.mappers.AnalyticsMapper;
import si.rso.analytics.lib.Analytics;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.*;

@ApplicationScoped
public class AnalyticsServiceImpl implements AnalyticsService {
    
    @PersistenceContext(unitName = "main-jpa-unit")
    private EntityManager em;
    
    @Override
    @Retry
    @CircuitBreaker
    @Timeout
    public Analytics getProductAnalytics(String productId){

        TypedQuery<AnalyticsEntity> query = em.createNamedQuery(AnalyticsEntity.FIND_BY_PRODUCT, AnalyticsEntity.class)
                .setParameter("productId", productId);;

        return AnalyticsMapper.fromEntity(query.getSingleResult());
    }
    
}