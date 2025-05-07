package org.intaehwang.dddstudy.chapter3;

import lombok.RequiredArgsConstructor;
import org.intaehwang.dddstudy.chapter1.Product;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
public class RegisterProductService {

    private final ProductRepository productRepository;
    private final StoreRepository storeRepository;

    public ProductId registerNewProduct(NewProductRequest req) {
        Store store = storeRepository.findById(req.getStoreId());

        checkNull(store);

        Product product = store.createProduct();
        productRepository.save(product);

        return product.getProductId();
    }

    private void checkNull(Store store) {}
}