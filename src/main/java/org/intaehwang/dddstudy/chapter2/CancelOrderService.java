package org.intaehwang.dddstudy.chapter2;


import org.intaehwang.dddstudy.chapter1.Order;
import org.intaehwang.dddstudy.chapter1.OrderState;
import org.intaehwang.dddstudy.chapter1.ShippingInfo;
import org.springframework.transaction.annotation.Transactional;

public class CancelOrderService {

    @Transactional
    public void cancelOrder(String orderId) {
        Order order = findOrderById(orderId);

        if (order == null) throw new OrderNotFoundException();

        order.cancel();
    }

    private Order findOrderById(String orderId) {
        return new Order(OrderState.CANCELED, new ShippingInfo());
    }
}
