package org.pchardwarestore.controller.catalogController;

import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.pchardwarestore.dto.catalogDto.categoryDto.CategoryResponse;
import org.pchardwarestore.service.catalogService.categoryService.FindCategoryService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@Validated
public class CategoryController {

    private final FindCategoryService findCategoryService;

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
}



