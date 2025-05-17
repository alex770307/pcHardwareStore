package org.pchardwarestore.service.catalogService.cotegorySectionService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pchardwarestore.dto.catalogDto.sectionDto.AddCategorySectionRequest;
import org.pchardwarestore.dto.catalogDto.sectionDto.CategorySectionResponse;
import org.pchardwarestore.entity.catalogEntity.CategorySection;
import org.pchardwarestore.repository.catalogRepository.CategorySectionRepository;
import org.pchardwarestore.service.exception.AlreadyExistException;
import org.pchardwarestore.service.util.CatalogConverter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AddCategorySectionServiceTest {

    private CategorySectionRepository categorySectionRepository;
    private CatalogConverter converter;
    private AddCategorySectionService addCategorySectionService;

    @BeforeEach
    void setUp() {
        categorySectionRepository = mock(CategorySectionRepository.class);
        converter = mock(CatalogConverter.class);
        addCategorySectionService = new AddCategorySectionService(categorySectionRepository, converter);
    }

    private AddCategorySectionRequest createRequest() {
        return new AddCategorySectionRequest("Core Components", "Main components for PC");
    }

    private CategorySection createSavedCategorySection() {
        CategorySection section = new CategorySection();
        section.setId(1L);
        section.setName("Core Components");
        section.setDescription("Main components for PC");
        return section;
    }

    private CategorySectionResponse createExpectedResponse() {
        return new CategorySectionResponse(1L, "Core Components", "Main components for PC");
    }

    @Test
    void addCategorySection_shouldSaveAndReturnResponse() {
        AddCategorySectionRequest request = createRequest();
        CategorySection categorySection = new CategorySection();
        CategorySection savedCategorySection = createSavedCategorySection();
        CategorySectionResponse response = createExpectedResponse();

        when(categorySectionRepository.existsByName(request.getName())).thenReturn(false);
        when(converter.toSection(request)).thenReturn(categorySection);
        when(categorySectionRepository.save(categorySection)).thenReturn(savedCategorySection);
        when(converter.fromSection(savedCategorySection)).thenReturn(response);

        CategorySectionResponse result = addCategorySectionService.addCategorySection(request);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Core Components", result.getName());
        assertEquals("Main components for PC", result.getDescription());

        verify(categorySectionRepository).save(categorySection);
    }

    @Test
    void addCategorySection_whenSectionAlreadyExists_shouldThrowException() {
        AddCategorySectionRequest request = createRequest();

        when(categorySectionRepository.existsByName(request.getName())).thenReturn(true);

        AlreadyExistException exception = assertThrows(AlreadyExistException.class,
                () -> addCategorySectionService.addCategorySection(request));

        assertEquals("Category section with name 'Core Components' already exists", exception.getMessage());
        verify(categorySectionRepository, never()).save(any());
    }

    @Test
    void addCategorySection_whenRequestIsNull_shouldThrowException() {
        assertThrows(NullPointerException.class, () -> addCategorySectionService.addCategorySection(null));
    }

    @Test
    void addCategorySection_whenRepositoryFails_shouldPropagateException() {
        AddCategorySectionRequest request = new AddCategorySectionRequest("Core Components", "Main components for PC");

        when(categorySectionRepository.existsByName(request.getName()))
                .thenThrow(new RuntimeException("Database is down"));

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> addCategorySectionService.addCategorySection(request));

        assertEquals("Database is down", exception.getMessage());
    }
}