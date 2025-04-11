package org.pchardwarestore.repository;

import org.pchardwarestore.entity.Category;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class CategoryRepositoryImpl implements CategoryRepository {

    private Map<Long, Category> categoryDatabase;
    private Long categoryId;

    public CategoryRepositoryImpl() {
        this.categoryDatabase = new HashMap<>();
        this.categoryId = 0L;
    }

    @Override
    public Category addCategory(Category category) {
        categoryId++;
        Category newCategory = new Category(categoryId,
                category.getName(),
                category.getDescription(),
                category.getCategoryType());
        categoryDatabase.put(categoryId, newCategory);
        return newCategory;
    }

    @Override
    public List<Category> findAllCategories() {
        return new ArrayList<>(categoryDatabase.values());
    }

    @Override
    public Optional<Category> findCategoryById(Long idFromCategory) {
        return Optional.ofNullable(categoryDatabase.get(idFromCategory));
    }

    @Override
    public List<Category> findCategoryByName(String categoryName) {
        return categoryDatabase.values().stream()
                .filter(category -> category.getName().equals(categoryName))
                .toList();
    }

    @Override
    public Optional<Category> updateCategory(Category categoryType) {
        Optional<Category> categoryForUpdate = categoryDatabase.values().stream()
                .filter(category -> category.getCategoryType().equals(categoryType))
                .findFirst();
        if (categoryForUpdate.isPresent()) {
            categoryForUpdate.get().setDescription(categoryType.getDescription());
            return categoryForUpdate;
        } else {
            return Optional.empty();
        }

    }

    @Override
    public Optional<Category> deleteCategoryById(Long idFromCategory) {
        return Optional.ofNullable(categoryDatabase.remove(idFromCategory));
    }
}
