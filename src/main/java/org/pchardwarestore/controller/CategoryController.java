package org.pchardwarestore.controller;

import org.pchardwarestore.dto.GeneralResponse;
import org.pchardwarestore.dto.categoryDto.CategoryRequestDto;
import org.pchardwarestore.dto.categoryDto.CategoryResponseDto;
import org.pchardwarestore.entity.categoryEntity.Category;
import org.pchardwarestore.service.categoryService.AddCategoryService;
import org.pchardwarestore.service.categoryService.DeleteCategoryService;
import org.pchardwarestore.service.categoryService.FindCategoryService;
import org.pchardwarestore.service.categoryService.UpdateCategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    private AddCategoryService addCategoryService;
    private DeleteCategoryService deleteCategoryService;
    private FindCategoryService findCategoryService;
    private UpdateCategoryService updateCategoryService;

    public CategoryController(AddCategoryService addCategoryService, DeleteCategoryService deleteCategoryService,
                              FindCategoryService findCategoryService, UpdateCategoryService updateCategoryService) {
        this.addCategoryService = addCategoryService;
        this.deleteCategoryService = deleteCategoryService;
        this.findCategoryService = findCategoryService;
        this.updateCategoryService = updateCategoryService;
    }
    @PostMapping()
    public GeneralResponse<CategoryResponseDto> createNewCategory(@RequestBody CategoryRequestDto request) {
        return addCategoryService.createNewCategory(request);
    }

    @GetMapping()
    public List<CategoryResponseDto> findAll() {
        return findCategoryService.findAll();
    }

    @GetMapping("/full")
    public List<Category> findAllFullDetails() {
        return findCategoryService.findAllFullDetails();
    }

    @GetMapping("/{id}")
    public GeneralResponse<CategoryResponseDto> findById(@PathVariable Long id) {
        GeneralResponse<CategoryResponseDto> categoryResponse = findCategoryService.findCategoryById(id);
            return new GeneralResponse<>(categoryResponse.getBody());
    }
    @GetMapping("/name")
    // запрос на путь /api/categories/name?name=name
    public GeneralResponse<List<CategoryResponseDto>> findByName(@RequestParam String name) {
        return findCategoryService.findCategoriesByName(name);

    }

    @GetMapping("/type")
    // запрос на путь /api/categories/type?categoryType=CPU
    public GeneralResponse<List<CategoryResponseDto>> findByType(@RequestParam String type) {
        return findCategoryService.findCategoriesByName(type);
    }

    @DeleteMapping("/{id}")
    public GeneralResponse<CategoryResponseDto> deleteCategory(@PathVariable Long id) {
        return deleteCategoryService.deleteCategoryById(id);
    }
}
