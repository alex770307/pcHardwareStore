package org.pchardwarestore.dto.catalogDto.productDto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.pchardwarestore.dto.catalogDto.categoryDto.CategoryResponse;
import org.pchardwarestore.entity.catalogEntity.ProductStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponse {

    private Long id;
    private String name;
    private String description;
    private String manufacturer;
    private Double price;
    private Integer quantity;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastUpdateDate;
    private ProductStatus status;
    private CategoryResponse category;
}
