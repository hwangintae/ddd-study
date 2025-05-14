package org.intaehwang.dddstudy.chapter4;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.intaehwang.dddstudy.chapter1.Money;

@Converter(autoApply = true)
public class MoneyConverter implements AttributeConverter<Money, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Money money) {
        return money == null ? null : money.getValue();
    }

    @Override
    public Money convertToEntityAttribute(Integer value) {
        return value == null ? null : new Money(value);
    }
}
