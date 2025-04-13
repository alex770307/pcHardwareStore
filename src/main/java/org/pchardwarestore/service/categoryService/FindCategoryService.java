package org.pchardwarestore.service.categoryService;

import org.pchardwarestore.dto.GeneralResponse;
import org.pchardwarestore.dto.categoryDto.CategoryResponseDto;
import org.pchardwarestore.entity.categoryEntity.Category;
import org.pchardwarestore.entity.categoryEntity.CategoryType;
import org.pchardwarestore.repository.categoryRepository.CategoryRepository;
import org.pchardwarestore.service.util.Converter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FindCategoryService {

    private CategoryRepository repository;
    private Converter converter;

    public FindCategoryService(CategoryRepository repository, Converter converter) {
        this.repository = repository;
        this.converter = converter;
    }

    public List<CategoryResponseDto> findAll() {
        return repository.findAllCategories().stream()
                .map(category -> converter.dtoFromCategory(category))
                .toList();
    }

    public List<Category> findAllFullDetails() {
        return repository.findAllCategories();
    }

    public GeneralResponse<CategoryResponseDto> findCategoryById(Long id) {
        Optional<Category> foundedCategoryOptional = repository.findCategoryById(id);
        if (foundedCategoryOptional.isPresent()) {
            CategoryResponseDto response = converter.dtoFromCategory(foundedCategoryOptional.get());
            return new GeneralResponse<>(response);
        }
        GeneralResponse<CategoryResponseDto> responseWithError = new GeneralResponse<>(null);
        responseWithError.addError("Category with id = " + id + " not found.");
        return responseWithError;
    }

    public GeneralResponse<List<CategoryResponseDto>> findCategoriesByName(String name) {
        List<Category> foundedCategories = repository.findCategoryByName(name);
        if (!foundedCategories.isEmpty()) {
            List<CategoryResponseDto> response = foundedCategories.stream()
                    .map(category ->converter.dtoFromCategory(category))
                    .toList();
            return new GeneralResponse<>(response);
        }
        GeneralResponse<List<CategoryResponseDto>> responseWithError = new GeneralResponse<>(null);
        responseWithError.addError("Category with name '" + name + "' not found.");
        return responseWithError;
    }

    public GeneralResponse<CategoryResponseDto> findCategoryByType(CategoryType type) {
        List<Category> foundedCategories = repository.findCategoryByCategoryType(type);
        if (!foundedCategories.isEmpty()) {
            CategoryResponseDto response = converter.dtoFromCategory(foundedCategories.get(0));
            return new GeneralResponse<>(response);
        }
        GeneralResponse<CategoryResponseDto> responseWithError = new GeneralResponse<>(null);
        responseWithError.addError("Category with type " + type + " not found.");
        return responseWithError;
    }
}
