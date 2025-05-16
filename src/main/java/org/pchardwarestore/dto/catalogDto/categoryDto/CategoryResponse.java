package org.pchardwarestorefour.dto.catalogDto.categoryDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.pchardwarestorefour.dto.catalogDto.sectionDto.CategorySectionResponse;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryResponse {
    private Long id;
    private String name;
    private String description;
    private CategorySectionResponse section;
}
