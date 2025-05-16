package org.pchardwarestorefour.service.catalogService.cotegorySectionService;

import lombok.AllArgsConstructor;
import org.pchardwarestorefour.dto.catalogDto.sectionDto.CategorySectionResponse;
import org.pchardwarestorefour.dto.catalogDto.sectionDto.UpdateCategorySectionRequest;
import org.pchardwarestorefour.entity.catalogEntity.CategorySection;
import org.pchardwarestorefour.repository.catalogRepository.CategorySectionRepository;
import org.pchardwarestorefour.service.exception.NotFoundException;
import org.pchardwarestorefour.service.util.CatalogConverter;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UpdateCategorySectionService {
    private CategorySectionRepository categorySectionRepository;
    private final CatalogConverter converter;

    public CategorySectionResponse updateCategorySection(UpdateCategorySectionRequest request, Long id) {
        CategorySection existing = categorySectionRepository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException("Category section with ID '" + id + "' not found"));
        if (request.getName() != null && !request.getName().isBlank()) {
            existing.setName(request.getName());
        }
        if (request.getDescription() != null && !request.getDescription().isBlank()) {
            existing.setDescription(request.getDescription());
        }

        CategorySection updated = categorySectionRepository.save(existing);
        return converter.fromSection(updated);
    }
}