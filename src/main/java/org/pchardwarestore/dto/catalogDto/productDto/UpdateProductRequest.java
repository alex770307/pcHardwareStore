package org.pchardwarestore.dto.catalogDto.productDto;


import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateProductRequest {

    @Column(name = "product_name")
    @Size(min = 3, max = 100, message = "Product name length must be between from 3 to 100 characters")
    private String name;

    @Size(min = 3, max = 255, message = "Product description length must be between from 3 to 255 characters")
    private String description;

    @Size(min = 3, max = 45, message = "Product manufacturer length must be between from 3 to 45 characters")
    private String manufacturer;

    @Positive(message = "Price must be positive")
    private Double price;

    @Min(value = 0, message = "Quantity cannot be negative")
    private Integer quantity;

    private String status;
}
