package org.pchardwarestore.controller.catalogController;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.pchardwarestore.dto.catalogDto.categoryDto.AddCategoryRequest;
import org.pchardwarestore.dto.catalogDto.categoryDto.CategoryResponse;
import org.pchardwarestore.dto.catalogDto.categoryDto.UpdateCategoryRequest;
import org.pchardwarestore.service.catalogService.categoryService.AddCategoryService;
import org.pchardwarestore.service.catalogService.categoryService.DeleteCategoryService;
import org.pchardwarestore.service.catalogService.categoryService.UpdateCategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categories-for-admin")
@AllArgsConstructor
@Validated
public class CategoryControllerForAdmin {

    private AddCategoryService addCategoryService;
    private DeleteCategoryService deleteCategoryService;
    private UpdateCategoryService updateCategoryService;

    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(@Valid @RequestBody AddCategoryRequest request) {
        CategoryResponse savedCategory = addCategoryService.addCategory(request);
        return ResponseEntity.status(201).body(savedCategory);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> updateCategory(
            @PathVariable Long id,
            @RequestBody @Valid UpdateCategoryRequest request
    ) {
        CategoryResponse updatedCategory = updateCategoryService.updateCategory(request, id);

        return ResponseEntity.ok(updatedCategory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CategoryResponse> deleteCategory(@PathVariable Long id) {
        CategoryResponse deletedCategory = deleteCategoryService.deleteCategory(id);
        return ResponseEntity.ok(deletedCategory);
    }
}
