package org.pchardwarestore.repository.categoryRepository;

import org.pchardwarestore.entity.categoryEntity.Category;
import org.pchardwarestore.entity.categoryEntity.CategoryType;
import org.pchardwarestore.entity.productEntity.ProductStatus;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository {

    Category addCategory(Category category);

    List<Category> findAllCategories();
    Optional<Category> findCategoryById(Long id);
    List<Category> findCategoryByName(String name);
    List<Category> findCategoryByCategoryType(CategoryType type);
//    Optional<Category> findCategoryByCategoryType(CategoryType type);

    Optional<Category> updateCategory(Category category);

    Optional<Category> deleteCategoryById(Long id);
}
