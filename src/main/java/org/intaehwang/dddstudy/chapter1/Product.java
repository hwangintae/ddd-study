package org.intaehwang.dddstudy.chapter1;

import jakarta.persistence.*;
import org.intaehwang.dddstudy.chapter3.CategoryId;
import org.intaehwang.dddstudy.chapter3.ProductId;

import java.util.Set;

@Entity
public class Product {

    @EmbeddedId
    private ProductId id;

    @ElementCollection
    @CollectionTable(name = "product_category",
    joinColumns = @JoinColumn(name = "product_id"))
    private Set<CategoryId> categoryIds;
}
