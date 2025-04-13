package org.pchardwarestore.service.categoryService;

import org.pchardwarestore.dto.GeneralResponse;
import org.pchardwarestore.dto.categoryDto.CategoryRequestDto;
import org.pchardwarestore.dto.categoryDto.CategoryResponseDto;
import org.pchardwarestore.entity.categoryEntity.Category;
import org.pchardwarestore.entity.categoryEntity.CategoryType;
import org.pchardwarestore.repository.categoryRepository.CategoryRepository;
import org.pchardwarestore.service.util.Converter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddCategoryService {

    private CategoryRepository repository;
    private Converter converter;

    public AddCategoryService(CategoryRepository repository, Converter converter) {
        this.repository = repository;
        this.converter = converter;
    }

    public GeneralResponse<CategoryResponseDto> createNewCategory(CategoryRequestDto request) {
        List<Category> existingCategory = repository.findCategoryByCategoryType(request.getCategoryType());
        if (isCategoryExists(request.getCategoryType())) {
            GeneralResponse<CategoryResponseDto> responseWithError = new GeneralResponse<>(null);
            responseWithError.addError("Category with type '" + request.getCategoryType() + "' already exists.");
            return responseWithError;
        }
        Category newCategory = converter.categoryFromDto(request);
        Category createdCategory = repository.addCategory(newCategory);
        CategoryResponseDto responseDto = converter.dtoFromCategory(createdCategory);
        GeneralResponse<CategoryResponseDto> response = new GeneralResponse<>(responseDto);
        return response;
    }

    private boolean isCategoryExists(CategoryType categoryType) {
        List<Category> existingCategory = repository.findCategoryByCategoryType(categoryType);
        return !existingCategory.isEmpty();
    }

}
