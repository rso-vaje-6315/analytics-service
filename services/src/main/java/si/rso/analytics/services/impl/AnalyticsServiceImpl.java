package si.rso.analytics.services.impl;

import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;
import si.rso.analytics.lib.AnalyticsStreamConfig;
import si.rso.analytics.persistence.AnalyticsEntity;
import si.rso.analytics.services.AnalyticsService;
import si.rso.analytics.mappers.AnalyticsMapper;
import si.rso.analytics.lib.Analytics;
import si.rso.event.streaming.EventStreamMessage;
import si.rso.event.streaming.JacksonMapper;

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

    @Retry
    @CircuitBreaker
    @Timeout
    @Override
    public void handleMessage(EventStreamMessage message) {
        if (message.getType().equals(AnalyticsStreamConfig.SEND_NOTIFICATION_EVENT_ID)) {

            em.getTransaction().begin();
            Analytics analytics = JacksonMapper.toEntity(message.getData(), Analytics.class);

            TypedQuery<AnalyticsEntity> query = em.createNamedQuery(AnalyticsEntity.FIND_BY_PRODUCT, AnalyticsEntity.class)
                    .setParameter("productId", analytics.getProductId());

            AnalyticsEntity analyticsEntity = query.getSingleResult();
            analyticsEntity.setNumberOfOrders(analyticsEntity.getNumberOfOrders() + analytics.getNumberOfOrders());
            analyticsEntity.setIncome(analyticsEntity.getIncome() + analytics.getIncome());

            em.merge(analyticsEntity);
            em.getTransaction().commit();
        } else {
            // type not handled by this service
        }
    }

}