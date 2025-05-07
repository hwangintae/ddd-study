package org.intaehwang.dddstudy.chapter3;

import org.intaehwang.dddstudy.chapter1.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public class ProductListService {

    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;

    public Page<Product> getProductOfCategory(Long categoryId, int page, int size) {
        Category category = categoryRepository.findById(categoryId);

        checkCategory(category);

        List<Product> products = productRepository.findByCategoryId(category.getId(), page, size);

        int totalCount = productRepository.countsByCategoryId(category.getId());

        return new PageImpl<>(
                products, PageRequest.of(page, size), totalCount
        );
    }

    private void checkCategory(Category category) {
    }
}
