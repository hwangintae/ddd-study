package org.intaehwang.dddstudy.chapter10;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderCanceledEvent extends Event {
    private String orderNumber;

    public OrderCanceledEvent(String orderNumber) {
        super();
        this.orderNumber = orderNumber;
    }
}
