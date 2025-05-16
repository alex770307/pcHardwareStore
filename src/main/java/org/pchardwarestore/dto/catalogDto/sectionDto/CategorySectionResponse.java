package org.pchardwarestore.dto.catalogDto.sectionDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategorySectionResponse {
    private Long id;
    private String name;
    private String description;
}
