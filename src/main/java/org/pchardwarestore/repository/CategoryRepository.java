package org.pchardwarestore.repository;


import org.pchardwarestore.entity.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository {

    Category addCategory(Category category);

    List<Category> findAllCategories();
    Optional<Category> findCategoryById(Long id);
    List<Category> findCategoryByName(String categoryName);

    Optional<Category> updateCategory(Category category);

    Optional<Category> deleteCategoryById(Long id);
}
