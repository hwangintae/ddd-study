package org.intaehwang.dddstudy.chapter2;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CalculateDiscountServiceTest {

    @Test
    public void noCustomer_thenExceptionShouldBeThrown() {
        // 테스트 목적의 대역 객체
        CustomerRepository stubRepo = mock(CustomerRepository.class);
        when(stubRepo.findById("noCustId")).thenReturn(null);

        RuleDiscounter stubRule = (cust, lines) -> null;

        // 대용 객체를 주입 받아 테스트 진행
        CalculateDiscountService calDisSvc = new CalculateDiscountService(stubRepo, stubRule);

        assertThrows(NoCustomerException.class,
                () -> calDisSvc.calculateDiscount(List.of(), "noCustId"));
    }

}