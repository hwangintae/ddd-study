package org.intaehwang.dddstudy.chapter2;

import org.intaehwang.dddstudy.chapter1.Money;
import org.intaehwang.dddstudy.chapter1.OrderLine;

import java.util.List;

public interface RuleDiscounter {
    Money applyRules(Customer customer, List<OrderLine> orderLines);
}
