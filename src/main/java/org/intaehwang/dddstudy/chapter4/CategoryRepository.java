package org.intaehwang.dddstudy.chapter4;

import org.intaehwang.dddstudy.chapter3.Category;
import org.intaehwang.dddstudy.chapter3.CategoryId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, CategoryId> {
}
