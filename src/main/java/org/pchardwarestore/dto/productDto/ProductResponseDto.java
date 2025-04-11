package org.pchardwarestore.dto.productDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.pchardwarestore.dto.categoryDto.CategoryResponseDto;
import org.pchardwarestore.entity.ProductStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseDto {

    private Long idForProduct;
    private String name;
    private String description;
    private String manufacturer;
    private Double price;
    private Integer quantity;
    private LocalDateTime createDate;
    private LocalDateTime lastUpdateDate;
    private ProductStatus status;
    private CategoryResponseDto categoryDto;
}
