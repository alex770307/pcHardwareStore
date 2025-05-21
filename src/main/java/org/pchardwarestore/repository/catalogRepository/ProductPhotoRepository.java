package org.pchardwarestore.repository.catalogRepository;

import org.pchardwarestore.entity.catalogEntity.ProductPhoto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductPhotoRepository extends JpaRepository<ProductPhoto, Long> {
}
