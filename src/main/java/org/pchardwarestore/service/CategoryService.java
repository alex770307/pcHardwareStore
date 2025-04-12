package org.pchardwarestore.service;

import org.pchardwarestore.entity.categoryEntity.Category;
import org.pchardwarestore.entity.categoryEntity.CategoryType;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    Category addCategory(Category category);

    List<Category> findAllCategories();
    Optional<Category> findCategoryById(long id);
    List<Category> findCategoryByCategoryName(String categoryName);
    Optional<Category> findCategoryByCategoryType(CategoryType type);

    Optional<Category> updateCategory(Category category);

    Optional<Category> deleteCategoryById(Long id);
}
