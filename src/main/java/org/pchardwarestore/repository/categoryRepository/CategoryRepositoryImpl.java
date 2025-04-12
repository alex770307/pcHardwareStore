package org.pchardwarestore.repository.categoryRepository;

import org.pchardwarestore.entity.categoryEntity.Category;
import org.pchardwarestore.entity.categoryEntity.CategoryType;
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

//    @Override
//    public Optional<Category> findCategoryByCategoryType(CategoryType type) {
//        return Optional.ofNullable(categoryDatabase.get(type));
//    }

    @Override
    public List<Category> findCategoryByCategoryType(CategoryType categoryType) {
        List<Category> categoriesByType = new ArrayList<>();
        categoryDatabase.values().stream()
                .filter(category -> category.getCategoryType().equals(categoryType))
                .forEach(category -> categoriesByType.add(category));
        return categoriesByType;
    }

    @Override
    public Optional<Category> updateCategory(Category category) {
        Long categoryId = category.getIdForCategory(); // Используем ID из переданного объекта
        Optional<Category> updatedCategory = findCategoryById(categoryId);
        if (updatedCategory.isPresent()) {
            updatedCategory.get().setName(category.getName());
            updatedCategory.get().setDescription(category.getDescription());
            updatedCategory.get().setCategoryType(category.getCategoryType());
            categoryDatabase.put(categoryId, updatedCategory.get());
            return Optional.of(updatedCategory.get());
        }
        return Optional.empty();
    }

    @Override
    public Optional<Category> deleteCategoryById(Long idFromCategory) {
        return Optional.ofNullable(categoryDatabase.remove(idFromCategory));
    }
}
