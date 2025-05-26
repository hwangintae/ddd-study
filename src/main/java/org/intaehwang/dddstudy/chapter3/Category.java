package org.intaehwang.dddstudy.chapter3;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class Category {

    @EmbeddedId
    private CategoryId categoryId;

    public CategoryId getId() {
        return categoryId;
    }

    public Category(CategoryId categoryId) {
        this.categoryId = categoryId;
    }
}
