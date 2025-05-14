package org.intaehwang.dddstudy.chapter4;

import jakarta.persistence.Embeddable;
import lombok.Builder;

public class Address {
    private String shippingAddress1;
    private String shippingAddress2;
    private String shippingZipcode;

    @Builder
    public Address(String shippingAddress1, String shippingAddress2, String shippingZipcode) {
        this.shippingAddress1 = shippingAddress1;
        this.shippingAddress2 = shippingAddress2;
        this.shippingZipcode = shippingZipcode;
    }
}
