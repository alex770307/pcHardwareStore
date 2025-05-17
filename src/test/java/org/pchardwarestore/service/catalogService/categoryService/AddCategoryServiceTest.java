package org.pchardwarestore.service.catalogService.categoryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pchardwarestore.dto.catalogDto.categoryDto.AddCategoryRequest;
import org.pchardwarestore.dto.catalogDto.categoryDto.CategoryResponse;
import org.pchardwarestore.dto.catalogDto.sectionDto.CategorySectionResponse;
import org.pchardwarestore.entity.catalogEntity.Category;
import org.pchardwarestore.entity.catalogEntity.CategorySection;
import org.pchardwarestore.repository.catalogRepository.CategoryRepository;
import org.pchardwarestore.service.catalogService.cotegorySectionService.FindCategorySectionService;
import org.pchardwarestore.service.exception.AlreadyExistException;
import org.pchardwarestore.service.util.CatalogConverter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AddCategoryServiceTest {

    private CategoryRepository categoryRepository;
    private FindCategorySectionService findCategorySectionService;
    private CatalogConverter converter;
    private AddCategoryService addCategoryService;

    @BeforeEach
    void setUp() {
        categoryRepository = mock(CategoryRepository.class);
        findCategorySectionService = mock(FindCategorySectionService.class);
        converter = mock(CatalogConverter.class);
        addCategoryService = new AddCategoryService(categoryRepository, findCategorySectionService, converter);
    }

    @Test
    void addCategory_shouldSaveAndReturnResponse() {
        AddCategoryRequest request = new AddCategoryRequest("Processors", "CPU products", "Core Components");
        CategorySection section = new CategorySection();
        Category category = new Category();
        Category savedCategory = new Category();
        savedCategory.setId(1L);
        savedCategory.setName("Processors");
        savedCategory.setDescription("CPU products");

        CategorySectionResponse sectionResponse = new CategorySectionResponse(10L, "Core Components", "Main PC components");
        CategoryResponse expectedResponse = new CategoryResponse(1L, "Processors", "CPU products",sectionResponse);

        when(categoryRepository.existsByName(request.getName())).thenReturn(false);
        when(findCategorySectionService.findByNameOrThrow("Core Components")).thenReturn(section);
        when(converter.toCategory(request, section)).thenReturn(category);
        when(categoryRepository.save(category)).thenReturn(savedCategory);
        when(converter.fromCategory(savedCategory)).thenReturn(expectedResponse);

        CategoryResponse result = addCategoryService.addCategory(request);

        assertNotNull(result);
        assertEquals(expectedResponse.getId(), result.getId());
        assertEquals(expectedResponse.getName(), result.getName());
        assertEquals(expectedResponse.getDescription(), result.getDescription());
        assertEquals(expectedResponse.getSection().getName(), result.getSection().getName());
        verify(categoryRepository).save(category);
    }

    @Test
    void addCategory_whenCategoryAlreadyExists_shouldThrowException() {
        AddCategoryRequest request = new AddCategoryRequest("Processors", "CPU products", "Core Components");
        when(categoryRepository.existsByName("Processors")).thenReturn(true);

        AlreadyExistException exception = assertThrows(AlreadyExistException.class,
                () -> addCategoryService.addCategory(request));

        assertEquals("Category with name 'Processors' already exists", exception.getMessage());
        verify(categoryRepository, never()).save(any());
    }
}