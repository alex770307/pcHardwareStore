package org.pchardwarestore.controller.catalogController;

import lombok.RequiredArgsConstructor;
import org.pchardwarestore.entity.catalogEntity.Product;
import org.pchardwarestore.repository.catalogRepository.ProductRepository;
import org.pchardwarestore.service.ProductFileService;
import org.pchardwarestore.service.catalogService.productService.FindProductService;
import org.pchardwarestore.service.exception.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class ProductFileUploadController {

    private final FindProductService service;
    private final ProductRepository productRepository;
    private final ProductFileService productFileService;

    @PostMapping("/api/products/{id}/upload-photo")
    public ResponseEntity<String> uploadProductPhoto(@PathVariable Long id,
                                                     @RequestParam("file") MultipartFile file) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Продукт не найден"));

        productFileService.storeFile(file, product);
        productRepository.save(product); // обязательно сохранить изменения

        return ResponseEntity.ok("Файл успешно загружен");
    }
}
