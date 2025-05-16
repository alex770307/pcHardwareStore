package org.pchardwarestorefour.service.catalogService.cotegorySectionService;

import lombok.AllArgsConstructor;
import org.pchardwarestorefour.dto.catalogDto.sectionDto.AddCategorySectionRequest;
import org.pchardwarestorefour.dto.catalogDto.sectionDto.CategorySectionResponse;
import org.pchardwarestorefour.entity.catalogEntity.CategorySection;
import org.pchardwarestorefour.repository.catalogRepository.CategorySectionRepository;
import org.pchardwarestorefour.service.exception.AlreadyExistException;
import org.pchardwarestorefour.service.util.CatalogConverter;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AddCategorySectionService {
    private CategorySectionRepository categorySectionRepository;
    private CatalogConverter converter;

    public CategorySectionResponse addCategorySection(AddCategorySectionRequest request) {
        if (categorySectionRepository.existsByName(request.getName())) {
            throw new AlreadyExistException("Category section with name '" + request.getName() + "' already exists");
        }
        CategorySection categorySection = converter.toSection(request);

        CategorySection saved = categorySectionRepository.save(categorySection);
        return converter.fromSection(saved);
    }
}
