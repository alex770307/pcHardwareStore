package org.pchardwarestore.controller.catalogController;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import org.pchardwarestore.dto.catalogDto.sectionDto.AddCategorySectionRequest;
import org.pchardwarestore.dto.catalogDto.sectionDto.CategorySectionResponse;
import org.pchardwarestore.dto.catalogDto.sectionDto.UpdateCategorySectionRequest;
import org.pchardwarestore.service.catalogService.cotegorySectionService.AddCategorySectionService;
import org.pchardwarestore.service.catalogService.cotegorySectionService.DeleteCategorySectionService;
import org.pchardwarestore.service.catalogService.cotegorySectionService.FindCategorySectionService;
import org.pchardwarestore.service.catalogService.cotegorySectionService.UpdateCategorySectionService;
import org.pchardwarestore.service.util.CatalogConverter;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/category-sections")
@AllArgsConstructor
@Validated
public class CategorySectionController {
    private AddCategorySectionService addCategorySectionService;
    private DeleteCategorySectionService deleteCategorySectionService;
    private FindCategorySectionService findCategorySectionService;
    private UpdateCategorySectionService updateCategorySectionService;
    private CatalogConverter converter;

    @PostMapping
    public ResponseEntity<CategorySectionResponse> addCategorySection(
            @RequestBody @Valid AddCategorySectionRequest request) {
        CategorySectionResponse savedCategorySection = addCategorySectionService.addCategorySection(request);
        return ResponseEntity.status(201).body(savedCategorySection);
    }

    @GetMapping
    public List<CategorySectionResponse> findAllCategorySections() {
        return findCategorySectionService.findAll();
    }

    @GetMapping("/{id}")
    public CategorySectionResponse findCategorySectionById(@PathVariable Long id) {
        return findCategorySectionService.findCategorySectionById(id);
    }

    // 🔍 GET /api/category-sections/search/by-sectionName?value=...
    @GetMapping("/search/by-name")
    public Optional<CategorySectionResponse> findCategorySectionBySectionName(
            @RequestParam("value") @NotBlank String sectionName) {
        return Optional.ofNullable(findCategorySectionService.findCategorySectionBySectionName(sectionName));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategorySectionResponse> updateCategorySection(
            @RequestBody @Valid UpdateCategorySectionRequest request,
            @PathVariable Long id

    ) {
        CategorySectionResponse updatedCategorySection = updateCategorySectionService
                .updateCategorySection(request, id);
        return ResponseEntity.ok(updatedCategorySection);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CategorySectionResponse> deleteCategorySection(@PathVariable Long id) {
        CategorySectionResponse deletedSection = deleteCategorySectionService.deleteCategorySection(id);
        return ResponseEntity.ok(deletedSection);
    }
}
