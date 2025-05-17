package org.pchardwarestore.dto.catalogDto.productDto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.pchardwarestore.dto.catalogDto.categoryDto.CategoryResponse;
import org.pchardwarestore.entity.catalogEntity.ProductStatus;

import java.time.LocalDateTime;
import java.util.List;

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
    //    todo
    private String photoLink;
    private List<String> photoLinks;

    private CategoryResponse category;



    public ProductResponse(Long id, String name, String description,
                           String manufacturer, Double price, Integer quantity,
                           LocalDateTime createDate, LocalDateTime lastUpdateDate,
                           ProductStatus status, CategoryResponse category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.manufacturer = manufacturer;
        this.price = price;
        this.quantity = quantity;
        this.createDate = createDate;
        this.lastUpdateDate = lastUpdateDate;
        this.status = status;
        this.category = category;
    }
}
