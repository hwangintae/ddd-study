package org.intaehwang.dddstudy.chapter4;

import lombok.Getter;

@Getter
public class Email {
    private String address;

    public Email(String address) {
        this.address = address;
    }
}
