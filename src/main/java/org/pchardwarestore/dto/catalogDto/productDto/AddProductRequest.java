package org.pchardwarestorefour.dto.catalogDto.productDto;

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
public class AddProductRequest {

    @Column(name = "product_name", nullable = false)
    @NotBlank(message = "Name cannot be empty")
    @Size(min = 3, max = 100, message = "Product name length must be between from 3 to 100 characters")
    private String name;

    @Column(nullable = false)
    @NotBlank(message = "Description cannot be empty")
    @Size(min = 3, max = 255, message = "Product description length must be between from 3 to 255 characters")
    private String description;

    @Column(nullable = false)
    @NotBlank(message = "Manufacturer cannot be empty")
    @Size(min = 3, max = 45, message = "Product manufacturer length must be between from 3 to 45 characters")
    private String manufacturer;

    @Column(nullable = false)
    @NotNull(message = "Price cannot be null")
    @Positive(message = "Price must be positive")
    private Double price;

    @Column(nullable = false)
    @NotNull(message = "Quantity cannot be null")
    @Min(value = 0, message = "Quantity cannot be negative")
    private Integer quantity;

    @Column(nullable = false)
    @NotBlank(message = "Category name is required")
    private String categoryName;

}