package org.pchardwarestore.repository.catalogRepository;


import org.pchardwarestore.entity.catalogEntity.CategorySection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategorySectionRepository extends JpaRepository<CategorySection, Long> {

    CategorySection findByName(String name);
    boolean existsByName(String name);
}
