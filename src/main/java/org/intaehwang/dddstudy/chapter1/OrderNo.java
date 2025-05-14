package org.intaehwang.dddstudy.chapter1;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@NoArgsConstructor
public class OrderNo implements Serializable {

    @Column(name = "order_number")
    private Long id;
}
