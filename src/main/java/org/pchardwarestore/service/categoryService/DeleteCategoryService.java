package org.pchardwarestore.service.categoryService;

import org.pchardwarestore.dto.GeneralResponse;
import org.pchardwarestore.dto.categoryDto.CategoryResponseDto;
import org.pchardwarestore.entity.categoryEntity.Category;
import org.pchardwarestore.repository.categoryRepository.CategoryRepository;
import org.pchardwarestore.service.util.Converter;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DeleteCategoryService {
    private CategoryRepository repository;
    private Converter converter;

    public DeleteCategoryService(CategoryRepository repository, Converter converter) {
        this.repository = repository;
        this.converter = converter;
    }

    public GeneralResponse<CategoryResponseDto> deleteCategoryById(Long id) {
        Optional<Category> deletedCategoryOptional = repository.deleteCategoryById(id);
        if (deletedCategoryOptional.isPresent()) {
            Category deletedCategory = deletedCategoryOptional.get();
            CategoryResponseDto responseDto = converter.dtoFromCategory(deletedCategory);
            return new GeneralResponse<>(responseDto);

        }
        GeneralResponse<CategoryResponseDto> generalResponse = new GeneralResponse<>(null);
        generalResponse.addError("Category with id = '" + id + "' not found.");
        return generalResponse;
    }
}
