package org.intaehwang.dddstudy.chapter1;

import java.util.List;

public class Order {
    private List<OrderLine> orderLines;
    private Money totalAmounts;

    private OrderState state;
    private ShippingInfo shippingInfo;

    private String orderNumber;

    public Order(List<OrderLine> orderLines, ShippingInfo shippingInfo) {
        setOrderLines(orderLines);
        setShippingInfo(shippingInfo);
    }

    public Order(OrderState state, ShippingInfo shippingInfo) {
        this.state = state;
        this.shippingInfo = new ShippingInfo();
    }

    public void changeShippingInfo(ShippingInfo newShippingInfo) {
        verifyNotYetShipped();
        setShippingInfo(newShippingInfo);
    }

    public void changeShipped() {}

    public void cancel() {
        verifyNotYetShipped();
        this.state = OrderState.CANCELED;
    }

    public void completePayment() {}

    private void setOrderLines(List<OrderLine> orderLines) {
        verifyAtLestOneOrMoreOrderLines(orderLines);
        this.orderLines = orderLines;
        calculateTotalAmounts();
    }

    private void setShippingInfo(ShippingInfo shippingInfo) {
        if (shippingInfo == null) {
            throw new IllegalArgumentException("no ShippingInfo");
        }
        this.shippingInfo = shippingInfo;
    }

    private void verifyAtLestOneOrMoreOrderLines(List<OrderLine> orderLines) {
        if (orderLines == null || orderLines.isEmpty()) {
            throw new IllegalArgumentException("no OrderLine");
        }
    }

    private void verifyNotYetShipped() {
        if (state != OrderState.PAYMENT_WAITING && state != OrderState.PREPARING) {
            throw new IllegalStateException("already shipped");
        }
    }

    private void calculateTotalAmounts() {
        int sum = orderLines.stream()
                .mapToInt(OrderLine::getAmounts)
                .sum();
        this.totalAmounts = new Money(sum);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (obj.getClass() != this.getClass()) return false;

        Order other = (Order) obj;
        if (this.orderNumber == null) return false;

        return this.orderNumber.equals(other.orderNumber);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;

        result = prime * result + ((this.orderNumber == null) ? 0 : this.orderNumber.hashCode());
        return result;
    }
}
