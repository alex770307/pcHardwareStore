package org.pchardwarestore.service.catalogService.cotegorySectionService;

import lombok.AllArgsConstructor;
import org.pchardwarestore.dto.catalogDto.sectionDto.CategorySectionResponse;
import org.pchardwarestore.dto.catalogDto.sectionDto.UpdateCategorySectionRequest;
import org.pchardwarestore.entity.catalogEntity.CategorySection;
import org.pchardwarestore.repository.catalogRepository.CategorySectionRepository;
import org.pchardwarestore.service.exception.NotFoundException;
import org.pchardwarestore.service.util.CatalogConverter;
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