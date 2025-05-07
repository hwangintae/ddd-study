package org.intaehwang.dddstudy.chapter3;

import org.intaehwang.dddstudy.chapter1.Money;
import org.intaehwang.dddstudy.chapter1.OrderLine;

import java.util.List;

public class OrderLines {
    private List<OrderLine> lines;

    public Money getTotalAmounts() {
        int sum = lines.stream()
                .mapToInt(OrderLine::getAmounts)
                .sum();

        return new Money(sum);
    }

    public void changeOrderLines(List<OrderLine> newLines) {
        verifyAtLestOneOrMoreOrderLines(newLines);
        this.lines = newLines;
    }

    private void verifyAtLestOneOrMoreOrderLines(List<OrderLine> orderLines) {
        if (orderLines == null || orderLines.isEmpty()) {
            throw new IllegalArgumentException("no OrderLines");
        }
    }
}
