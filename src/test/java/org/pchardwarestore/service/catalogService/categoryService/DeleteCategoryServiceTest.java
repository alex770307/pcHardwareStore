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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class DeleteCategoryServiceTest {

    private CategoryRepository categoryRepository;
    private CatalogConverter converter;
    private DeleteCategoryService deleteCategoryService;

    @BeforeEach
    void setUp() {
        categoryRepository = mock(CategoryRepository.class);
        converter = mock(CatalogConverter.class);
        deleteCategoryService = new DeleteCategoryService(categoryRepository, converter);
    }

    @Test
    void deleteCategory_whenCategoryExists_shouldDeleteAndReturnResponse() {
        Long categoryId = 1L;

        CategorySection section = new CategorySection(10L, "Core Components", "Main PC parts");
        Category category = new Category(categoryId, "Processors", "High-performance CPUs", section);

        CategorySectionResponse sectionResponse = new CategorySectionResponse(10L, "Core Components", "Main PC parts");
        CategoryResponse expectedResponse = new CategoryResponse(categoryId, "Processors", "High-performance CPUs", sectionResponse);

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        when(converter.fromCategory(category)).thenReturn(expectedResponse);

        CategoryResponse result = deleteCategoryService.deleteCategory(categoryId);

        verify(categoryRepository).delete(category);
        assertEquals(expectedResponse, result);
    }

    @Test
    void deleteCategory_whenCategoryNotFound_shouldThrowNotFoundException() {

        Long categoryId = 999L;
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> deleteCategoryService.deleteCategory(categoryId));
        verify(categoryRepository, never()).delete(any());
    }
}