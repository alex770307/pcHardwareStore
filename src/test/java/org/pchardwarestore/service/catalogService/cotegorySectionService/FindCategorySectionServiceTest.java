package org.pchardwarestore.service.catalogService.cotegorySectionService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pchardwarestore.dto.catalogDto.sectionDto.CategorySectionResponse;
import org.pchardwarestore.entity.catalogEntity.CategorySection;
import org.pchardwarestore.repository.catalogRepository.CategorySectionRepository;
import org.pchardwarestore.service.exception.NotFoundException;
import org.pchardwarestore.service.util.CatalogConverter;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FindCategorySectionServiceTest {

    private CategorySectionRepository categorySectionRepository;
    private CatalogConverter converter;
    private FindCategorySectionService findCategorySectionService;

    @BeforeEach
    void setUp() {
        categorySectionRepository = mock(CategorySectionRepository.class);
        converter = mock(CatalogConverter.class);
        findCategorySectionService = new FindCategorySectionService(categorySectionRepository, converter);
    }

    @Test
    void findAll_shouldReturnListOfCategorySectionResponses() {
        CategorySection section = new CategorySection();
        section.setId(1L);
        section.setName("Core Components");
        section.setDescription("Main components for PC");

        List<CategorySection> sections = Collections.singletonList(section);
        CategorySectionResponse response = new CategorySectionResponse(1L, "Core Components", "Main components for PC");

        when(categorySectionRepository.findAll()).thenReturn(sections);
        when(converter.fromSection(section)).thenReturn(response);

        List<CategorySectionResponse> result = findCategorySectionService.findAll();

        assertEquals(1, result.size());
        assertEquals(response.getId(), result.get(0).getId());
        assertEquals(response.getName(), result.get(0).getName());
        assertEquals(response.getDescription(), result.get(0).getDescription());
    }

    @Test
    void findCategorySectionById_shouldReturnCategorySectionResponse() {
        Long id = 1L;
        CategorySection section = new CategorySection();
        section.setId(id);
        section.setName("Core Components");
        section.setDescription("Main components for PC");

        CategorySectionResponse response = new CategorySectionResponse(id, "Core Components", "Main components for PC");

        when(categorySectionRepository.findById(id)).thenReturn(Optional.of(section));
        when(converter.fromSection(section)).thenReturn(response);

        CategorySectionResponse result = findCategorySectionService.findCategorySectionById(id);

        assertNotNull(result);
        assertEquals(response.getId(), result.getId());
        assertEquals(response.getName(), result.getName());
        assertEquals(response.getDescription(), result.getDescription());
    }

    @Test
    void findCategorySectionById_whenNotFound_shouldThrowNotFoundException() {

        Long id = 1L;
        when(categorySectionRepository.findById(id)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> findCategorySectionService.findCategorySectionById(id));

        assertEquals("Category section with ID '" + id + "' not found", exception.getMessage());
    }

    @Test
    void findCategorySectionBySectionName_shouldReturnCategorySectionResponse() {

        String sectionName = "Core Components";
        CategorySection section = new CategorySection();
        section.setName(sectionName);

        CategorySectionResponse response = new CategorySectionResponse(1L, sectionName, "Main components for PC");

        when(categorySectionRepository.findByName(sectionName)).thenReturn(section);
        when(converter.fromSection(section)).thenReturn(response);

        CategorySectionResponse result = findCategorySectionService.findCategorySectionBySectionName(sectionName);

        assertNotNull(result);
        assertEquals(response.getName(), result.getName());
    }

    @Test
    void findCategorySectionBySectionName_whenNotFound_shouldThrowNotFoundException() {

        String sectionName = "Core Components";
        when(categorySectionRepository.findByName(sectionName)).thenReturn(null);

        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> findCategorySectionService.findCategorySectionBySectionName(sectionName));

        assertEquals("Category section with name '" + sectionName + "' not found", exception.getMessage());
    }

    @Test
    void findByNameOrThrow_shouldReturnCategorySection() {

        String sectionName = "Core Components";
        CategorySection section = new CategorySection();
        section.setName(sectionName);

        when(categorySectionRepository.findByName(sectionName)).thenReturn(section);

        CategorySection result = findCategorySectionService.findByNameOrThrow(sectionName);

        assertNotNull(result);
        assertEquals(sectionName, result.getName());
    }

    @Test
    void findByNameOrThrow_whenNotFound_shouldThrowNotFoundException() {

        String sectionName = "Core Components";
        when(categorySectionRepository.findByName(sectionName)).thenReturn(null);

        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> findCategorySectionService.findByNameOrThrow(sectionName));

        assertEquals("Category section with name '" + sectionName + "' does not exist", exception.getMessage());
    }
}