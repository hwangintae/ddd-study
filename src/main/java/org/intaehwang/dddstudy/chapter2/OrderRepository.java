package org.intaehwang.dddstudy.chapter2;

import org.intaehwang.dddstudy.chapter1.Order;
import org.intaehwang.dddstudy.chapter1.OrderNo;

public interface OrderRepository {
    Order findByNumber(OrderNo no);
    void save(Order order);
    void delete(Order order);
}
