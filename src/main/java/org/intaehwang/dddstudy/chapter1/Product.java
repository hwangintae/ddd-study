package org.intaehwang.dddstudy.chapter1;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import org.intaehwang.dddstudy.chapter3.CategoryId;
import org.intaehwang.dddstudy.chapter3.ProductId;
import org.intaehwang.dddstudy.chapter4.Image;

import java.util.ArrayList;
import java.util.List;
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

    @ElementCollection
    @CollectionTable(name = "product_image",
            joinColumns = @JoinColumn(name = "product_id"))
    private List<Image> images = new ArrayList<>();

    public void changeImages(List<Image> newImages) {
        images.clear();
        images.addAll(newImages);
    }
}
