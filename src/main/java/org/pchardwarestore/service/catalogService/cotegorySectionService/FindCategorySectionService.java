package org.pchardwarestore.service.catalogService.cotegorySectionService;

import lombok.AllArgsConstructor;
import org.pchardwarestore.dto.catalogDto.sectionDto.CategorySectionResponse;
import org.pchardwarestore.entity.catalogEntity.CategorySection;
import org.pchardwarestore.repository.catalogRepository.CategorySectionRepository;
import org.pchardwarestore.service.exception.NotFoundException;
import org.pchardwarestore.service.util.CatalogConverter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FindCategorySectionService {
    private CategorySectionRepository categorySectionRepository;
    private CatalogConverter converter;

    public List<CategorySectionResponse> findAll() {
        return categorySectionRepository.findAll().stream()
                .map(converter::fromSection)
                .collect(Collectors.toList());
    }

    public CategorySectionResponse findCategorySectionById(Long id) {
        return categorySectionRepository.findById(id)
                .map(converter::fromSection)
                .orElseThrow(() -> new NotFoundException("Category section with ID '" + id + "' not found"));
    }

    public CategorySectionResponse findCategorySectionBySectionName(String sectionName) {
        return Optional.ofNullable(categorySectionRepository.findByName(sectionName))
                .map(converter::fromSection)
                .orElseThrow(()-> new NotFoundException("Category section with name '" + sectionName + "' not found"));
    }

    public CategorySection findByNameOrThrow(String sectionName) {
        CategorySection section = categorySectionRepository.findByName(sectionName);
        if (section == null) {
            throw new NotFoundException("Category section with name '" + sectionName + "' does not exist");
        }
        return section;
    }
}

