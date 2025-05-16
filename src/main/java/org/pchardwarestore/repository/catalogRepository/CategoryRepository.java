package org.pchardwarestorefour.repository.catalogRepository;


import org.pchardwarestorefour.entity.catalogEntity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findAllBySection_Name( String sectionName);

    List<Category> findAllBySection_Id(Long sectionId);

    Optional<Category> findByName(String name);

    boolean existsByName(String name);
}
