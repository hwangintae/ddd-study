package org.intaehwang.dddstudy.chapter1;

import lombok.Getter;

@Getter
public class ShippingInfo {
    private Receiver receiver;
    private String shippingAddress1;
    private String shippingAddress2;
    private String shippingZipcode;

    public ShippingInfo() {}

    public ShippingInfo(Receiver receiver, String shippingAddress1, String shippingAddress2, String shippingZipcode) {
        this.receiver = receiver;
        this.shippingAddress1 = shippingAddress1;
        this.shippingAddress2 = shippingAddress2;
        this.shippingZipcode = shippingZipcode;
    }
}
