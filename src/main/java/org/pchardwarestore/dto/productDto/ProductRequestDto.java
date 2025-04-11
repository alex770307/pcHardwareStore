package org.pchardwarestore.dto.productDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.pchardwarestore.entity.Category;
import org.pchardwarestore.entity.ProductStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequestDto {
    private String name;
    private String description;
    private String manufacturer;
    private Double price;
    private Integer quantity;
    private ProductStatus status;
    private Category category;

}
