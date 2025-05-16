package org.pchardwarestore.service.catalogService.cotegorySectionService;

import lombok.AllArgsConstructor;
import org.pchardwarestore.dto.catalogDto.sectionDto.CategorySectionResponse;
import org.pchardwarestore.entity.catalogEntity.CategorySection;
import org.pchardwarestore.repository.catalogRepository.CategorySectionRepository;
import org.pchardwarestore.service.exception.NotFoundException;
import org.pchardwarestore.service.util.CatalogConverter;
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

