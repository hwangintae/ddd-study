package org.intaehwang.dddstudy.chapter3;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
public class ProductId {

    @Column(name = "product_id")
    private Long id;

    public ProductId(Long id) {
        this.id = id;
    }
}
