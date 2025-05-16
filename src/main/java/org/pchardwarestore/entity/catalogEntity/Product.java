package org.pchardwarestore.entity.catalogEntity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "product")

public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
    @NotNull(message = "CreateDate cannot be null")
    private LocalDateTime createDate;

    @Column(nullable = false)
    @NotNull(message = "LastUpdateDate cannot be null")
    private LocalDateTime lastUpdateDate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull(message = "ProductStatus cannot be null")
    private ProductStatus status;
//todo
    private String photoLink;

    @Builder.Default
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProductInfo> photos = new HashSet<>();


    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    @NotNull(message = "Category cannot be null")
    private Category category;

    public Product(String name, String description, String manufacturer,
                   Double price, Integer quantity, LocalDateTime createDate,
                   LocalDateTime lastUpdateDate, ProductStatus status,
                   String photoLink, Set<ProductInfo> photos, Category category) {
        this.name = name;
        this.description = description;
        this.manufacturer = manufacturer;
        this.price = price;
        this.quantity = quantity;
        this.createDate = createDate;
        this.lastUpdateDate = lastUpdateDate;
        this.status = status;
        this.photoLink = photoLink;
        this.photos = photos;
        this.category = category;
    }

    public Product(String name, String description, String manufacturer,
                   Double price, Integer quantity, String photoLink,
                   Set<ProductInfo> photos, Category category) {
        this.name = name;
        this.description = description;
        this.manufacturer = manufacturer;
        this.price = price;
        this.quantity = quantity;
        this.photoLink = photoLink;
        this.photos = photos;
        this.category = category;
    }


}

