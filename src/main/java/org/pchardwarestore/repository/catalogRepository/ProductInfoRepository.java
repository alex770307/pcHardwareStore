package org.pchardwarestore.repository.catalogRepository;

import org.pchardwarestore.entity.catalogEntity.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductInfoRepository extends JpaRepository<ProductInfo, Long> {
}
