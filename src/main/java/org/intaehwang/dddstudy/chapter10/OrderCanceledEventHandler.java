package org.intaehwang.dddstudy.chapter10;

import lombok.RequiredArgsConstructor;
import org.intaehwang.dddstudy.chapter2.OrderRepository;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderCanceledEventHandler {
    private final RefundService refundService;

    @EventListener(OrderCanceledEvent.class)
    public void handle(OrderCanceledEvent event) {
        refundService.refund(event.getOrderNumber());
    }
}
