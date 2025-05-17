package org.pchardwarestore.controller.catalogController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.pchardwarestore.dto.catalogDto.productDto.AddProductRequest;
import org.pchardwarestore.dto.catalogDto.productDto.ProductResponse;
import org.pchardwarestore.dto.catalogDto.productDto.UpdateProductRequest;
import org.pchardwarestore.service.catalogService.productService.AddProductService;
import org.pchardwarestore.service.catalogService.productService.DeleteProductService;
import org.pchardwarestore.service.catalogService.productService.UpdateProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products-for-manager")
@RequiredArgsConstructor
@Validated
public class ProductControllerForManager {

    private final AddProductService addProductService;
    private final UpdateProductService updateProductService;
    private final DeleteProductService deleteProductService;

    @PostMapping
    public ResponseEntity<ProductResponse> addProduct(@Valid @RequestBody AddProductRequest request) {
        ProductResponse saved = addProductService.addProduct(request);
        return ResponseEntity.status(201).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody UpdateProductRequest request
    ) {
        ProductResponse updatedProduct = updateProductService.updateProduct(request, id);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProductResponse> deleteProduct(@PathVariable Long id) {
        ProductResponse deletedProduct = deleteProductService.deleteProduct(id);
        return ResponseEntity.ok(deletedProduct);
    }
}
