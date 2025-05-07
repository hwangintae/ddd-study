package org.intaehwang.dddstudy.chapter3;

import org.intaehwang.dddstudy.chapter1.Product;

public class Store {

    public Product createProduct() {
        if (isBlocked()) throw new StoreBlockedException();

        return new Product();
    }

    public boolean isBlocked() {
        return false;
    }
}
