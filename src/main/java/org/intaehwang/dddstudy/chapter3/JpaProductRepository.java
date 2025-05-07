package org.intaehwang.dddstudy.chapter3;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.intaehwang.dddstudy.chapter1.Product;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JpaProductRepository implements ProductRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Product> findByCategoryId(CategoryId catId, int page, int size) {
        TypedQuery<Product> query = em.createQuery(
                "select p from Product p " +
                        "where :catId member of p.categoryIds order by p.id.id desc",
                Product.class);

        query.setParameter("catId", catId);
        query.setFirstResult((page - 1) * size);
        query.setMaxResults(size);
        return query.getResultList();
    }

    @Override
    public int countsByCategoryId(CategoryId id) {
        return 0;
    }
}
