package org.pchardwarestore.service;

import lombok.RequiredArgsConstructor;
import org.pchardwarestore.repository.catalogRepository.ProductInfoRepository;
import org.pchardwarestore.service.catalogService.productService.FindProductService;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class ProductFileService {
    private final ProductInfoRepository repository;
    private final FindProductService findProductService;
    private final Path FileStorageLocation = Paths.get("src/main/resources/static/upload/product_img");
}
