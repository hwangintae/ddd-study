package org.intaehwang.dddstudy.chapter1;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor
public class ShippingInfo {
    private Receiver receiver;
    private String shippingAddress1;
    private String shippingAddress2;
    private String shippingZipcode;

    public ShippingInfo(Receiver receiver, String shippingAddress1, String shippingAddress2, String shippingZipcode) {
        this.receiver = receiver;
        this.shippingAddress1 = shippingAddress1;
        this.shippingAddress2 = shippingAddress2;
        this.shippingZipcode = shippingZipcode;
    }
}
