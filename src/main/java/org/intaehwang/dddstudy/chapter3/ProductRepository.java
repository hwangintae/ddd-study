package org.intaehwang.dddstudy.chapter3;

import org.intaehwang.dddstudy.chapter1.Product;

import java.util.List;

public interface ProductRepository {

    List<Product> findByCategoryId(CategoryId id, int page, int size);

     int countsByCategoryId(CategoryId id);
}
