package org.intaehwang.dddstudy.chapter1;

import java.util.List;

public class Order {
    private List<OrderLine> orderLines;
    private Money totalAmounts;

    private OrderState state;
    private ShippingInfo shippingInfo;

    public Order(List<OrderLine> orderLines) {
        setOrderLines(orderLines);
    }

    public Order(OrderState state, ShippingInfo shippingInfo) {
        this.state = state;
        this.shippingInfo = new ShippingInfo();
    }

    public void changeShippingInfo(ShippingInfo newShippingInfo) {
        if (!isShippingChangeable()) {
            throw new IllegalStateException("can't change shipping in " + state);
        }

        this.shippingInfo = newShippingInfo;
    }

    private boolean isShippingChangeable() {
        return state == OrderState.PAYMENT_WAITING ||
                state == OrderState.PREPARING;
    }

    public void changeShipped() {}
    public void cancel() {}
    public void completePayment() {}

    private void setOrderLines(List<OrderLine> orderLines) {
        verifyAtLestOneOrMoreOrderLines(orderLines);
        this.orderLines = orderLines;
        calculateTotalAmounts();
    }

    private void verifyAtLestOneOrMoreOrderLines(List<OrderLine> orderLines) {
        if (orderLines == null || orderLines.isEmpty()) {
            throw new IllegalArgumentException("no OrderLine");
        }
    }

    private void calculateTotalAmounts() {
        int sum = orderLines.stream()
                .mapToInt(OrderLine::getAmounts)
                .sum();
        this.totalAmounts = new Money(sum);
    }
}
