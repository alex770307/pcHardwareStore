package org.pchardwarestore.entity.categoryEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {

    private Long idForCategory;

    private String name;

    private String description;

    private CategoryType categoryType;

    public Category(String name, String description, CategoryType categoryType) {
        this.name = name;
        this.description = description;
        this.categoryType = categoryType;
    }
}
