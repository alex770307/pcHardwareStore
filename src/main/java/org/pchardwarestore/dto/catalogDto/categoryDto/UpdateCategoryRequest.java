package org.pchardwarestore.dto.catalogDto.categoryDto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateCategoryRequest {

    @Column(name = "category_name")
    @Size(min = 3, max = 45, message = "Category name length must be between from 3 to 45 characters")
    private String name;

    @Size(min = 3, max = 255, message = "Category description length must be between from 3 to 255 characters")
    private String description;
}
