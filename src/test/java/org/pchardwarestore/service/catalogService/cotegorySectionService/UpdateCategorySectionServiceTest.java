package org.pchardwarestore.service.catalogService.cotegorySectionService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pchardwarestore.dto.catalogDto.sectionDto.CategorySectionResponse;
import org.pchardwarestore.dto.catalogDto.sectionDto.UpdateCategorySectionRequest;
import org.pchardwarestore.entity.catalogEntity.CategorySection;
import org.pchardwarestore.repository.catalogRepository.CategorySectionRepository;
import org.pchardwarestore.service.exception.NotFoundException;
import org.pchardwarestore.service.util.CatalogConverter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UpdateCategorySectionServiceTest {

    private CategorySectionRepository categorySectionRepository;
    private CatalogConverter converter;
    private UpdateCategorySectionService updateCategorySectionService;

    @BeforeEach
    void setUp() {
        categorySectionRepository = mock(CategorySectionRepository.class);
        converter = mock(CatalogConverter.class);
        updateCategorySectionService = new UpdateCategorySectionService(categorySectionRepository, converter);
    }

    @Test
    void updateCategorySection_shouldUpdateAndReturnResponse() {
        Long id = 1L;
        UpdateCategorySectionRequest request = new UpdateCategorySectionRequest("Updated Name", "Updated Description");

        CategorySection existingSection = new CategorySection();
        existingSection.setId(id);
        existingSection.setName("Old Name");
        existingSection.setDescription("Old Description");

        CategorySection updatedSection = new CategorySection();
        updatedSection.setId(id);
        updatedSection.setName("Updated Name");
        updatedSection.setDescription("Updated Description");

        CategorySectionResponse expectedResponse = new CategorySectionResponse(id, "Updated Name", "Updated Description");

        when(categorySectionRepository.findById(id)).thenReturn(java.util.Optional.of(existingSection));
        when(categorySectionRepository.save(existingSection)).thenReturn(updatedSection);
        when(converter.fromSection(updatedSection)).thenReturn(expectedResponse);

        CategorySectionResponse result = updateCategorySectionService.updateCategorySection(request, id);

        assertNotNull(result);
        assertEquals(expectedResponse.getId(), result.getId());
        assertEquals(expectedResponse.getName(), result.getName());
        assertEquals(expectedResponse.getDescription(), result.getDescription());

        verify(categorySectionRepository).findById(id);
        verify(categorySectionRepository).save(existingSection);
        verify(converter).fromSection(updatedSection);
    }

    @Test
    void updateCategorySection_whenSectionNotFound_shouldThrowException() {

        Long id = 1L;
        UpdateCategorySectionRequest request = new UpdateCategorySectionRequest("Updated Name", "Updated Description");

        when(categorySectionRepository.findById(id)).thenReturn(java.util.Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> updateCategorySectionService.updateCategorySection(request, id));

        assertEquals("Category section with ID '" + id + "' not found", exception.getMessage());

        verify(categorySectionRepository).findById(id);
        verify(categorySectionRepository, never()).save(any());
        verify(converter, never()).fromSection(any());
    }

}