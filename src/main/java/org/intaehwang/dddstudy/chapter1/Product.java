package org.intaehwang.dddstudy.chapter1;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import org.intaehwang.dddstudy.chapter3.CategoryId;
import org.intaehwang.dddstudy.chapter3.ProductId;

import java.util.Set;

@Entity
@NoArgsConstructor
public class Product {

    @EmbeddedId
    private ProductId id;

    @ElementCollection
    @CollectionTable(name = "product_category",
    joinColumns = @JoinColumn(name = "product_id"))
    private Set<CategoryId> categoryIds;

    public Product(Set<CategoryId> categoryIds) {
        this.categoryIds = categoryIds;
    }

    public ProductId getProductId() {
        return id;
    }
}
