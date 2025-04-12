package org.pchardwarestore.service;

import org.pchardwarestore.dto.GeneralResponse;
import org.pchardwarestore.dto.categoryDto.CategoryRequestDto;
import org.pchardwarestore.dto.categoryDto.CategoryResponseDto;
import org.pchardwarestore.entity.categoryEntity.Category;
import org.pchardwarestore.entity.categoryEntity.CategoryType;
import org.pchardwarestore.repository.categoryRepository.CategoryRepository;
import org.pchardwarestore.service.util.Converter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    private CategoryRepository categoryRepository;
    private Converter converter;

    public CategoryService(CategoryRepository categoryRepository, Converter converter) {
        this.categoryRepository = categoryRepository;
        this.converter = converter;
    }


    public GeneralResponse<CategoryResponseDto> createNewCategory(CategoryRequestDto request) {

        List<Category> existingCategory = categoryRepository.findCategoryByCategoryType(request.getCategoryType());

        if (!existingCategory.isEmpty()){
            GeneralResponse<CategoryResponseDto> response = new GeneralResponse<>(null);
            response.addError("Категория с типом '" + request.getCategoryType() + "' уже существует.");
            return response;
        }

        Category newCategory = converter.categoryFromDto(request);

        Category createdCategory = categoryRepository.addCategory(newCategory);

        CategoryResponseDto responseDto =  converter.dtoFromCategory(createdCategory);

        GeneralResponse<CategoryResponseDto> response = new GeneralResponse<>(responseDto);

        return response;
    }


    public Category addCategory(Category category) {
        return null;
    }

    public List<Category> findAllCategories() {
        return List.of();
    }

    public Optional<Category> findCategoryById(long id) {
        return Optional.empty();
    }

    public List<Category> findCategoryByCategoryName(String categoryName) {
        return List.of();
    }

    public Optional<Category> findCategoryByCategoryType(CategoryType type) {
        return Optional.empty();
    }

    public Optional<Category> updateCategory(Category category) {
        return Optional.empty();
    }

    public Optional<Category> deleteCategoryById(Long id) {
        return Optional.empty();
    }
}
