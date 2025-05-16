package org.pchardwarestorefour.service.catalogService.categoryService;

import lombok.AllArgsConstructor;

import org.pchardwarestorefour.dto.catalogDto.categoryDto.AddCategoryRequest;
import org.pchardwarestorefour.dto.catalogDto.categoryDto.CategoryResponse;
import org.pchardwarestorefour.entity.catalogEntity.Category;
import org.pchardwarestorefour.entity.catalogEntity.CategorySection;
import org.pchardwarestorefour.repository.catalogRepository.CategoryRepository;
import org.pchardwarestorefour.service.catalogService.cotegorySectionService.FindCategorySectionService;
import org.pchardwarestorefour.service.exception.AlreadyExistException;
import org.pchardwarestorefour.service.util.CatalogConverter;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AddCategoryService {
    private CategoryRepository categoryRepository;
    private FindCategorySectionService findCategorySectionService;
    private CatalogConverter converter;

    public CategoryResponse addCategory(AddCategoryRequest request) {
        if (categoryRepository.existsByName(request.getName())) {
            throw new AlreadyExistException("Category with name '" + request.getName() + "' already exists");
        }
        CategorySection section = findCategorySectionService.findByNameOrThrow(request.getSectionName());
        Category category = converter.toCategory(request, section);
        Category saved = categoryRepository.save(category);
        return converter.fromCategory(saved);
    }
}


