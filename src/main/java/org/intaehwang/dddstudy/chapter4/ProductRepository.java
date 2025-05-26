package org.intaehwang.dddstudy.chapter4;

import org.intaehwang.dddstudy.chapter1.Product;
import org.intaehwang.dddstudy.chapter3.ProductId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, ProductId> {
}
