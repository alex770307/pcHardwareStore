package org.pchardwarestore.controller.catalogController;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import org.pchardwarestore.dto.catalogDto.categoryDto.AddCategoryRequest;
import org.pchardwarestore.dto.catalogDto.categoryDto.CategoryResponse;
import org.pchardwarestore.dto.catalogDto.categoryDto.UpdateCategoryRequest;
import org.pchardwarestore.service.catalogService.categoryService.AddCategoryService;
import org.pchardwarestore.service.catalogService.categoryService.DeleteCategoryService;
import org.pchardwarestore.service.catalogService.categoryService.FindCategoryService;
import org.pchardwarestore.service.catalogService.categoryService.UpdateCategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/categories")
@AllArgsConstructor
@Validated
public class CategoryController {

    private AddCategoryService addCategoryService;
    private DeleteCategoryService deleteCategoryService;
    private FindCategoryService findCategoryService;
    private UpdateCategoryService updateCategoryService;

    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(@Valid @RequestBody AddCategoryRequest request) {
        CategoryResponse savedCategory = addCategoryService.addCategory(request);
        return ResponseEntity.status(201).body(savedCategory);
    }

    @GetMapping
    public List<CategoryResponse> findAllCategories() {
        return findCategoryService.findAll();
    }

    @GetMapping("/{id}")
    public CategoryResponse findCategoryById(@PathVariable Long id) {
        return findCategoryService.findCategoryById(id);
    }

    // 🔍 GET /api/categories/search/by-name?value=...
    @GetMapping("/search/by-name")
    public Optional<CategoryResponse> findCategoryByName(@RequestParam("value") @NotBlank String name) {
        return Optional.ofNullable(findCategoryService.findByCategoryName(name));
    }

    // 🔍 GET /api/categories/search/by-sectionName?value=...
    @GetMapping("/search/by-sectionName")
    public List<CategoryResponse> findCategoryBySectionName(@RequestParam("value") @NotBlank String sectionName) {
        return findCategoryService.findBySectionName(sectionName);
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



