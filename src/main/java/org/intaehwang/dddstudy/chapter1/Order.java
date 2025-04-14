package org.intaehwang.dddstudy.chapter1;

public class Order {
    private OrderState state;
    private ShippingInfo shippingInfo;

    public Order(OrderState state, ShippingInfo shippingInfo) {
        this.state = state;
        this.shippingInfo = new ShippingInfo();
    }

    public void changeShippingInfo(ShippingInfo newShippingInfo) {
        if (!state.isShippingChangeable()) {
            throw new IllegalStateException("can't change shipping in " + state);
        }

        this.shippingInfo = newShippingInfo;
    }
}
