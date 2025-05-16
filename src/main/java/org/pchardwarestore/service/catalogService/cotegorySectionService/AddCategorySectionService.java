package org.pchardwarestore.service.catalogService.cotegorySectionService;

import lombok.AllArgsConstructor;
import org.pchardwarestore.dto.catalogDto.sectionDto.AddCategorySectionRequest;
import org.pchardwarestore.dto.catalogDto.sectionDto.CategorySectionResponse;
import org.pchardwarestore.entity.catalogEntity.CategorySection;
import org.pchardwarestore.repository.catalogRepository.CategorySectionRepository;
import org.pchardwarestore.service.exception.AlreadyExistException;
import org.pchardwarestore.service.util.CatalogConverter;
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
