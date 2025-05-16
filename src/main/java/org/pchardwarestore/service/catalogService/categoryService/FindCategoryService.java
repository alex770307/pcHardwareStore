package org.pchardwarestorefour.service.catalogService.categoryService;

import lombok.AllArgsConstructor;
import org.pchardwarestorefour.dto.catalogDto.categoryDto.CategoryResponse;
import org.pchardwarestorefour.entity.catalogEntity.Category;
import org.pchardwarestorefour.repository.catalogRepository.CategoryRepository;
import org.pchardwarestorefour.service.exception.NotFoundException;
import org.pchardwarestorefour.service.util.CatalogConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FindCategoryService {
    private CategoryRepository categoryRepository;
    private CatalogConverter converter;

    public List<CategoryResponse> findAll() {
        return categoryRepository.findAll().stream()
                .map(response -> converter.fromCategory(response))
                .collect(Collectors.toList());
    }

    public CategoryResponse findCategoryById(Long id) {
        return categoryRepository.findById(id)
                .map(response -> converter.fromCategory(response))
                .orElseThrow(() -> new NotFoundException("Category with ID '" + id + "' not found"));
    }

    public CategoryResponse findByCategoryName(String categoryName) {
        return categoryRepository.findByName(categoryName)
                .map(response -> converter.fromCategory(response))
                .orElseThrow(()->new NotFoundException("Category section with name '" + categoryName + "' not found"));
    }

    public Category findByNameOrThrow(String categoryName) {
        Optional<Category> category = categoryRepository.findByName(categoryName);
        if (category.isPresent()) {
            return category.get();
        }
        throw new NotFoundException("Category with name '" + categoryName + "' does not exist");
    }

    public List<CategoryResponse> findBySectionName(String sectionName) {
        if (StringUtils.hasText(sectionName)) {
            return categoryRepository.findAllBySection_Name(sectionName)
                    .stream()
                    .map(response -> converter.fromCategory(response))
                    .collect(Collectors.toList());
        } else {
            throw new NotFoundException("Section name '" + sectionName + "' is null or empty");
        }
    }
}