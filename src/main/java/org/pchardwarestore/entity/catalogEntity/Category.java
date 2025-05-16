package org.pchardwarestorefour.entity.catalogEntity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "category_name", nullable = false)
    @NotBlank(message = "Name cannot be empty")
    @Size(min = 3, max = 45, message = "Category name length must be between from 3 to 45 characters")
    private String name;

    @Column(nullable = false)
    @NotBlank(message = "Description cannot be empty")
    @Size(min = 3, max = 255, message = "Category description length must be between from 3 to 255 characters")
    private String description;

    @ManyToOne
    @JoinColumn(name = "section_id", nullable = false)
    @NotNull(message = "CategorySection cannot be null")
    private CategorySection section;

    public Category(String name, String description, CategorySection section) {
        this.name = name;
        this.description = description;
        this.section = section;
    }
}
