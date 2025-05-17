package org.pchardwarestore.controller.catalogController;

import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.pchardwarestore.dto.catalogDto.sectionDto.CategorySectionResponse;
import org.pchardwarestore.service.catalogService.cotegorySectionService.FindCategorySectionService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/category-sections")
@RequiredArgsConstructor
@Validated
public class CategorySectionController {
    private final FindCategorySectionService findCategorySectionService;

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

}
