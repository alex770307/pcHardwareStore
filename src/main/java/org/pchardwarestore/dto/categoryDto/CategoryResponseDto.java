package org.pchardwarestore.dto.categoryDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.pchardwarestore.entity.CategoryType;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResponseDto {

    private Long idForCategory;
    private String name;
    private String description;
    private CategoryType categoryType;
}
