package org.intaehwang.dddstudy.chapter2;

import org.intaehwang.dddstudy.chapter1.Money;

public class MutableMoney {
    private int value;

    public MutableMoney(int value) {
        this.value = value;
    }

    public int add(int value) {
        return this.value += value;
    }

    public int multiply(int multiplier) {
        return this.value *= multiplier;
    }

    public Money toImmutableMoney() {
        return new Money(this.value);
    }
}
