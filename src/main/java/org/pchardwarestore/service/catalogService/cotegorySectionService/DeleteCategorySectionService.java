package org.pchardwarestorefour.service.catalogService.cotegorySectionService;

import lombok.AllArgsConstructor;
import org.pchardwarestorefour.dto.catalogDto.sectionDto.CategorySectionResponse;
import org.pchardwarestorefour.entity.catalogEntity.CategorySection;
import org.pchardwarestorefour.repository.catalogRepository.CategorySectionRepository;
import org.pchardwarestorefour.service.exception.NotFoundException;
import org.pchardwarestorefour.service.util.CatalogConverter;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DeleteCategorySectionService {
    private CategorySectionRepository categorySectionRepository;
    private CatalogConverter converter;

    public CategorySectionResponse deleteCategorySection(Long id) {
        CategorySection categorySection = categorySectionRepository.findById(id)
        .orElseThrow(() -> new NotFoundException("Category section with ID  '" + id + "' not found"));
                categorySectionRepository.delete(categorySection);
                return converter.fromSection(categorySection);
    }
}

