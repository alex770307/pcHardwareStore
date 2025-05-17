package org.pchardwarestore.controller.catalogController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.pchardwarestore.dto.catalogDto.sectionDto.AddCategorySectionRequest;
import org.pchardwarestore.dto.catalogDto.sectionDto.CategorySectionResponse;
import org.pchardwarestore.dto.catalogDto.sectionDto.UpdateCategorySectionRequest;
import org.pchardwarestore.service.catalogService.cotegorySectionService.AddCategorySectionService;
import org.pchardwarestore.service.catalogService.cotegorySectionService.DeleteCategorySectionService;
import org.pchardwarestore.service.catalogService.cotegorySectionService.UpdateCategorySectionService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/sections-for-admin")
@RequiredArgsConstructor
@Validated
public class CategorySectionControllerForAdmin {
    private final AddCategorySectionService addCategorySectionService;
    private final DeleteCategorySectionService deleteCategorySectionService;
    private final UpdateCategorySectionService updateCategorySectionService;

    @PostMapping
    public ResponseEntity<CategorySectionResponse> addCategorySection(
            @RequestBody @Valid AddCategorySectionRequest request) {
        CategorySectionResponse savedCategorySection = addCategorySectionService.addCategorySection(request);
        return ResponseEntity.status(201).body(savedCategorySection);
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
