package org.intaehwang.dddstudy.chapter3;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor
public class CategoryId {

    private Long id;

    public CategoryId(Long id) {
        this.id = id;
    }
}
