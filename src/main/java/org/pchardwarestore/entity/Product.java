package org.pchardwarestore.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    private Long idForProduct;
    private String name;
    private String description;
    private String manufacturer;
    private Double price;
    private Integer quantity;
    private LocalDateTime createDate;
    private LocalDateTime lastUpdateDate;
    private ProductStatus status;
    private Category category;

    public Product(String name, String description, String manufacturer,
                   Double price, Integer quantity, LocalDateTime createDate,
                   LocalDateTime lastUpdateDate, ProductStatus status,
                   Category category) {
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
