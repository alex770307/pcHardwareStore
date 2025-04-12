package org.pchardwarestore.service;

import org.pchardwarestore.entity.categoryEntity.Category;
import org.pchardwarestore.entity.categoryEntity.CategoryType;
import org.pchardwarestore.repository.categoryRepository.CategoryRepository;
import org.pchardwarestore.service.util.Converter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    private CategoryRepository categoryRepository;
    private Converter converter;

    public CategoryServiceImpl(CategoryRepository categoryRepository, Converter converter) {
        this.categoryRepository = categoryRepository;
        this.converter = converter;
    }

    @Override
    public Category addCategory(Category category) {

        return null;
    }

    @Override
    public List<Category> findAllCategories() {
        return List.of();
    }

    @Override
    public Optional<Category> findCategoryById(long id) {
        return Optional.empty();
    }

    @Override
    public List<Category> findCategoryByCategoryName(String categoryName) {
        return List.of();
    }

    @Override
    public Optional<Category> findCategoryByCategoryType(CategoryType type) {
        return Optional.empty();
    }

    @Override
    public Optional<Category> updateCategory(Category category) {
        return Optional.empty();
    }

    @Override
    public Optional<Category> deleteCategoryById(Long id) {
        return Optional.empty();
    }
}
