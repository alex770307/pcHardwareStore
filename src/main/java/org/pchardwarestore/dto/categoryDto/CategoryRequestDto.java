package org.pchardwarestore.dto.categoryDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.pchardwarestore.entity.CategoryType;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryRequestDto {

    private String name;
    private String description;
    private CategoryType categoryType;
}
