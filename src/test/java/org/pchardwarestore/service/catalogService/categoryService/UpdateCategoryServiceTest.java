package org.pchardwarestore.service.catalogService.categoryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pchardwarestore.dto.catalogDto.categoryDto.CategoryResponse;
import org.pchardwarestore.dto.catalogDto.categoryDto.UpdateCategoryRequest;
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

class UpdateCategoryServiceTest {

    private CategoryRepository categoryRepository;
    private CatalogConverter converter;
    private UpdateCategoryService updateCategoryService;

    @BeforeEach
    void setUp() {
        categoryRepository = mock(CategoryRepository.class);
        converter = mock(CatalogConverter.class);
        updateCategoryService = new UpdateCategoryService(categoryRepository, converter);
    }

    @Test
    void updateCategory_shouldUpdateAndReturnResponse_whenCategoryExists() {
        Long id = 1L;
        UpdateCategoryRequest request = new UpdateCategoryRequest("Updated Name", "Updated Description");

        Category existingCategory = new Category(id, "Old Name", "Old Description", new CategorySection());
        Category updatedCategory = new Category(id, "Updated Name", "Updated Description", existingCategory.getSection());
        CategoryResponse expectedResponse = new CategoryResponse(id, "Updated Name", "Updated Description", null);

        when(categoryRepository.findById(id)).thenReturn(Optional.of(existingCategory));
        when(categoryRepository.save(existingCategory)).thenReturn(updatedCategory);
        when(converter.fromCategory(updatedCategory)).thenReturn(expectedResponse);

        CategoryResponse result = updateCategoryService.updateCategory(request, id);

        assertEquals(expectedResponse, result);
        verify(categoryRepository).findById(id);
        verify(categoryRepository).save(existingCategory);
        verify(converter).fromCategory(updatedCategory);
    }

    @Test
    void updateCategory_shouldThrowException_whenCategoryNotFound() {
        Long id = 2L;
        UpdateCategoryRequest request = new UpdateCategoryRequest("Name", "Description");
        when(categoryRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> updateCategoryService.updateCategory(request, id));
        verify(categoryRepository).findById(id);
        verify(categoryRepository, never()).save(any());
        verify(converter, never()).fromCategory(any());
    }
}