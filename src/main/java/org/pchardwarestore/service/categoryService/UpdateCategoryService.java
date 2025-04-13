package org.pchardwarestore.service.categoryService;

import org.pchardwarestore.dto.categoryDto.CategoryResponseDto;
import org.pchardwarestore.entity.categoryEntity.Category;
import org.pchardwarestore.repository.categoryRepository.CategoryRepository;
import org.pchardwarestore.service.util.Converter;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UpdateCategoryService {

    private CategoryRepository repository;
    private Converter converter;

    public UpdateCategoryService(CategoryRepository repository, Converter converter) {
        this.repository = repository;
        this.converter = converter;
    }

    public Optional<CategoryResponseDto> updateCategory(Category category) {
        return Optional.empty();
    }
}
