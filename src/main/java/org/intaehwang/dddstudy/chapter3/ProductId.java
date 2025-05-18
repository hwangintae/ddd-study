package org.intaehwang.dddstudy.chapter3;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class ProductId {

    @Column(name = "product_id")
    private Long id;
}
