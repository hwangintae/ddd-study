package org.intaehwang.dddstudy.chapter3;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
public class CategoryId {

    private Long id;
}
