package org.pchardwarestore.service.catalogService.cotegorySectionService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pchardwarestore.dto.catalogDto.sectionDto.CategorySectionResponse;
import org.pchardwarestore.entity.catalogEntity.CategorySection;
import org.pchardwarestore.repository.catalogRepository.CategorySectionRepository;
import org.pchardwarestore.service.exception.NotFoundException;
import org.pchardwarestore.service.util.CatalogConverter;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class DeleteCategorySectionServiceTest {

    private CategorySectionRepository categorySectionRepository;
    private CatalogConverter converter;
    private DeleteCategorySectionService deleteCategorySectionService;

    @BeforeEach
    void setUp() {
        categorySectionRepository = mock(CategorySectionRepository.class);
        converter = mock(CatalogConverter.class);
        deleteCategorySectionService = new DeleteCategorySectionService(categorySectionRepository, converter);
    }

    @Test
    void deleteCategorySection_shouldDeleteAndReturnResponse() {
        Long id = 1L;
        CategorySection categorySection = new CategorySection();
        categorySection.setId(id);
        categorySection.setName("Core Components");
        categorySection.setDescription("Main components for PC");

        CategorySectionResponse expectedResponse = new CategorySectionResponse(id, "Core Components", "Main components for PC");

        when(categorySectionRepository.findById(id)).thenReturn(Optional.of(categorySection));
        when(converter.fromSection(categorySection)).thenReturn(expectedResponse);

        CategorySectionResponse actualResponse = deleteCategorySectionService.deleteCategorySection(id);

        assertNotNull(actualResponse);
        assertEquals(expectedResponse.getId(), actualResponse.getId());
        assertEquals(expectedResponse.getName(), actualResponse.getName());
        assertEquals(expectedResponse.getDescription(), actualResponse.getDescription());

        verify(categorySectionRepository).delete(categorySection);
    }

    @Test
    void deleteCategorySection_whenSectionNotFound_shouldThrowNotFoundException() {

        Long id = 1L;

        when(categorySectionRepository.findById(id)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> deleteCategorySectionService.deleteCategorySection(id));

        assertEquals("Category section with ID  '" + id + "' not found", exception.getMessage());

        verify(categorySectionRepository, never()).delete(any());
    }
}