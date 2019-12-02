package si.rso.analytics.persistence;

import javax.persistence.*;

@Entity
@Table(name = "analytics")
@NamedQueries(value = {
    @NamedQuery(name = AnalyticsEntity.FIND_BY_PRODUCT, query = "SELECT a FROM AnalyticsEntity a WHERE a.productId = :productId")
})
public class AnalyticsEntity extends BaseEntity {

    public static final String FIND_BY_PRODUCT = "AnalyticsEntity.findByProduct";

    @Column(name = "product_id")
    private String productId;

    @Column(name = "number_of_orders")
    private int numberOfOrders;

    @Column(name = "income")
    private double income;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getNumberOfOrders() {
        return numberOfOrders;
    }

    public void setNumberOfOrders(int numberOfOrders) {
        this.numberOfOrders = numberOfOrders;
    }

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }
}