package org.pchardwarestore.controller.catalogController;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import org.pchardwarestore.dto.catalogDto.productDto.AddProductRequest;
import org.pchardwarestore.dto.catalogDto.productDto.ProductResponse;
import org.pchardwarestore.dto.catalogDto.productDto.UpdateProductRequest;
import org.pchardwarestore.entity.catalogEntity.ProductStatus;
import org.pchardwarestore.service.catalogService.productService.AddProductService;
import org.pchardwarestore.service.catalogService.productService.DeleteProductService;
import org.pchardwarestore.service.catalogService.productService.FindProductService;
import org.pchardwarestore.service.catalogService.productService.UpdateProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@AllArgsConstructor
@Validated
public class ProductController {
    private AddProductService addProductService;
    private UpdateProductService updateProductService;
    private DeleteProductService deleteProductService;
    private FindProductService findProductService;

    @PostMapping
    public ResponseEntity<ProductResponse> addProduct(@Valid @RequestBody AddProductRequest request) {
        ProductResponse saved = addProductService.addProduct(request);
        return ResponseEntity.status(201).body(saved);
    }

    @GetMapping
    public List<ProductResponse> findAllProducts() {
        return findProductService.findAll();
    }

    @GetMapping("/{id}")
    public ProductResponse findProductById(@PathVariable Long id) {
        return findProductService.findProductById(id);
    }

    // 🔍 GET /api/products/search/by-name?value=...
    @GetMapping("/search/by-name")
    public List<ProductResponse> findProductByName(@RequestParam("value") @NotBlank String name) {
        return findProductService.findProductByName(name);
    }

    // 🔍 GET /api/products/search/by-manufacturer?value=...
    @GetMapping("/search/by-manufacturer")
    public List<ProductResponse> findProductByManufacturer(@RequestParam("value") @NotBlank String manufacturer) {
        return findProductService.findByManufacturer(manufacturer);
    }

    // 🔍 GET /api/products/search/by-price-range?min=...&max=...
    @GetMapping("/search/by-price-range")
    public List<ProductResponse> findProductByPrice(@RequestParam("min") Double lower,
                                                    @RequestParam("max") Double upper) {
        return findProductService.findByPriceBetween(lower, upper);
    }

    // 🔍 GET /api/products/search/by-quantity?min=...
    @GetMapping("/search/by-quantity")
    public List<ProductResponse> findProductByQuantity(@RequestParam("min") Integer quantity) {
        return findProductService.findByQuantityGreaterThan(quantity);
    }

    // 🔍 GET /api/products/search/by-status?value=AVAILABLE
    @GetMapping("/search/by-status")
    public List<ProductResponse> findProductByStatus(@RequestParam("value") ProductStatus status) {
        return findProductService.findByStatus(status);
    }

    // 🔍 GET /api/products/search/by-category?value=CPU
    @GetMapping("/search/by-category")
    public List<ProductResponse> findProductByCategoryName(@RequestParam("value") @NotBlank String categoryName) {
        return findProductService.findByCategoryName(categoryName);
    }

    // 🔍 GET /api/products/search/by-section?value=...
    @GetMapping("/search/by-section")
    public List<ProductResponse> findProductBySectionName(@RequestParam("value") @NotBlank String sectionName) {
        return findProductService.findBySectionName(sectionName);
    }

    // 🔍 GET /api/products/search?manufacturer=Intel&minPrice=100&maxPrice=500&status=AVAILABLE&category=CPU
    @GetMapping("/search")
    public List<ProductResponse> searchProducts(
            @RequestParam(required = false) String manufacturer,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) ProductStatus status,
            @RequestParam(required = false) String category
    ) {
        return findProductService.searchProducts(manufacturer, minPrice, maxPrice, status, category);
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
