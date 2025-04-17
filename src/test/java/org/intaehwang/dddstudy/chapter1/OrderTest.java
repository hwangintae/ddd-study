package org.intaehwang.dddstudy.chapter1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class OrderTest {

    @Test
    @DisplayName("OrderState가 SHIPPED 일때 IllegalStateException이 발생한다.")
    public void changeShippingInfoStateShipped() {
        // given
        Order order = new Order(OrderState.SHIPPED, new ShippingInfo());

        // expected
        assertThatThrownBy(() -> order.changeShippingInfo(new ShippingInfo()))
                .hasMessage("can't change shipping in SHIPPED")
                .isInstanceOf(IllegalStateException.class);
    }

}