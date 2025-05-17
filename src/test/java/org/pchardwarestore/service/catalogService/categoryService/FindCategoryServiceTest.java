package org.pchardwarestore.service.catalogService.categoryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pchardwarestore.dto.catalogDto.categoryDto.CategoryResponse;
import org.pchardwarestore.dto.catalogDto.sectionDto.CategorySectionResponse;
import org.pchardwarestore.entity.catalogEntity.Category;
import org.pchardwarestore.entity.catalogEntity.CategorySection;
import org.pchardwarestore.repository.catalogRepository.CategoryRepository;
import org.pchardwarestore.service.exception.NotFoundException;
import org.pchardwarestore.service.util.CatalogConverter;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FindCategoryServiceTest {

    private CategoryRepository categoryRepository;
    private CatalogConverter converter;
    private FindCategoryService findCategoryService;

    @BeforeEach
    void setUp() {
        categoryRepository = mock(CategoryRepository.class);
        converter = mock(CatalogConverter.class);
        findCategoryService = new FindCategoryService(categoryRepository, converter);
    }

    @Test
    void findAll_shouldReturnListOfCategoryResponses() {
        CategorySection section = new CategorySection(1L, "Core", "Main");
        Category category = new Category(1L, "GPU", "Graphics", section);
        CategoryResponse response = new CategoryResponse(1L, "GPU", "Graphics", new CategorySectionResponse(1L, "Core", "Main"));

        when(categoryRepository.findAll()).thenReturn(List.of(category));
        when(converter.fromCategory(category)).thenReturn(response);

        List<CategoryResponse> result = findCategoryService.findAll();

        assertEquals(1, result.size());
        assertEquals(response, result.get(0));
    }

    @Test
    void findCategoryById_shouldReturnResponse_whenExists() {
        CategorySection section = new CategorySection(1L, "Core", "Main");
        Category category = new Category(1L, "GPU", "Graphics", section);
        CategoryResponse response = new CategoryResponse(1L, "GPU", "Graphics", new CategorySectionResponse(1L, "Core", "Main"));

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(converter.fromCategory(category)).thenReturn(response);

        CategoryResponse result = findCategoryService.findCategoryById(1L);

        assertEquals(response, result);
    }

    @Test
    void findCategoryById_shouldThrowException_whenNotFound() {
        when(categoryRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> findCategoryService.findCategoryById(99L));
    }

    @Test
    void findByCategoryName_shouldReturnResponse_whenFound() {
        CategorySection section = new CategorySection(1L, "Core", "Main");
        Category category = new Category(1L, "GPU", "Graphics", section);
        CategoryResponse response = new CategoryResponse(1L, "GPU", "Graphics", new CategorySectionResponse(1L, "Core", "Main"));

        when(categoryRepository.findByName("GPU")).thenReturn(Optional.of(category));
        when(converter.fromCategory(category)).thenReturn(response);

        CategoryResponse result = findCategoryService.findByCategoryName("GPU");

        assertEquals(response, result);
    }

    @Test
    void findByCategoryName_shouldThrowException_whenNotFound() {
        when(categoryRepository.findByName("Unknown")).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> findCategoryService.findByCategoryName("Unknown"));
    }

    @Test
    void findByNameOrThrow_shouldReturnCategory_whenFound() {
        Category category = new Category(1L, "GPU", "Graphics", new CategorySection());
        when(categoryRepository.findByName("GPU")).thenReturn(Optional.of(category));

        Category result = findCategoryService.findByNameOrThrow("GPU");

        assertEquals(category, result);
    }

    @Test
    void findByNameOrThrow_shouldThrowException_whenNotFound() {
        when(categoryRepository.findByName("Unknown")).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> findCategoryService.findByNameOrThrow("Unknown"));
    }

    @Test
    void findBySectionName_shouldReturnList_whenSectionNameIsValid() {
        CategorySection section = new CategorySection(1L, "Core", "Main");
        Category category = new Category(1L, "GPU", "Graphics", section);
        CategoryResponse response = new CategoryResponse(1L, "GPU", "Graphics", new CategorySectionResponse(1L, "Core", "Main"));

        when(categoryRepository.findAllBySection_Name("Core")).thenReturn(List.of(category));
        when(converter.fromCategory(category)).thenReturn(response);

        List<CategoryResponse> result = findCategoryService.findBySectionName("Core");

        assertEquals(1, result.size());
        assertEquals(response, result.get(0));
    }

    @Test
    void findBySectionName_shouldThrowException_whenSectionNameIsNullOrEmpty() {
        assertThrows(NotFoundException.class, () -> findCategoryService.findBySectionName(""));
        assertThrows(NotFoundException.class, () -> findCategoryService.findBySectionName(null));
    }
}