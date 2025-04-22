package org.intaehwang.dddstudy.chapter2;

import org.intaehwang.dddstudy.chapter1.Money;
import org.intaehwang.dddstudy.chapter1.OrderLine;

import java.util.Arrays;
import java.util.List;

public class CalculateDiscountService {
    private DroolsRuleEngine ruleEngine;

    public CalculateDiscountService(DroolsRuleEngine ruleEngine) {
        this.ruleEngine = new DroolsRuleEngine();
    }

    public Money calculateDiscount(List<OrderLine> orderLines, String customerId) {
        Customer customer = findCustomer(customerId);

        MutableMoney money = new MutableMoney(0);
        List<Object> facts = Arrays.asList(customer, money);

        facts.addAll(orderLines);
        ruleEngine.evalute("discountCalculation", facts);
        return money.toImmutableMoney();
    }

    private Customer findCustomer(String customerId) {
        return new Customer();
    }

}
