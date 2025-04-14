package org.pchardwarestore.dto.productDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.pchardwarestore.entity.categoryEntity.Category;
import org.pchardwarestore.entity.categoryEntity.CategoryType;
import org.pchardwarestore.entity.productEntity.ProductStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequestDto {
    private String name;
    private String description;
    private String manufacturer;
    private Double price;
    private Integer quantity;
    private Long categoryID;
//    private CategoryType categoryType;
}
