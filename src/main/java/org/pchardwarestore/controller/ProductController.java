package org.pchardwarestore.controller;

import org.pchardwarestore.dto.GeneralResponse;
import org.pchardwarestore.dto.productDto.ProductRequestDto;
import org.pchardwarestore.dto.productDto.ProductResponseDto;
import org.pchardwarestore.entity.categoryEntity.CategoryType;
import org.pchardwarestore.entity.productEntity.Product;
import org.pchardwarestore.entity.productEntity.ProductStatus;
import org.pchardwarestore.service.productService.AddProductService;
import org.pchardwarestore.service.productService.DeleteProductService;
import org.pchardwarestore.service.productService.FindProductService;
import org.pchardwarestore.service.productService.UpdateProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private AddProductService addProductService;
    private DeleteProductService deleteProductService;
    private FindProductService findProductService;
    private UpdateProductService updateProductService;

    public ProductController(AddProductService addProductService, DeleteProductService deleteProductService,
                             FindProductService findProductService, UpdateProductService updateProductService) {
        this.addProductService = addProductService;
        this.deleteProductService = deleteProductService;
        this.findProductService = findProductService;
        this.updateProductService = updateProductService;
    }
    @PostMapping
    public GeneralResponse<ProductResponseDto> createProduct(@RequestBody ProductRequestDto productRequestDto) {
        return addProductService.addNewProduct(productRequestDto);
    }
    @GetMapping
    public List<ProductResponseDto> findAllProducts() {
        return findProductService.findAllProducts();
    }
    @GetMapping("/full")
    public List<Product> findAllDetails(){
        return findProductService.findAllDetails();
    }
    @GetMapping("/{id}")
    public GeneralResponse<ProductResponseDto> findProductById(@PathVariable Long id) {
        return findProductService.findProductById(id);
    }

    @GetMapping("/name")
    //    Пример запроса:   /api/products/name?name=Intel Core i9-13900K
    public GeneralResponse<List<ProductResponseDto>> findByName(@RequestParam String name) {
        return findProductService.findProductByName(name);
    }

    @GetMapping("/manufacturer")
//    Пример запроса:   /api/products/manufacturer?name=Intel
    public GeneralResponse<List<ProductResponseDto>> findByManufacturer(@RequestParam String name) {
        return findProductService.findProductByManufacturer(name);
    }

    @GetMapping("/category-type")
    //    Пример запроса:   /api/products/category-type?type=CPU
    public GeneralResponse<List<ProductResponseDto>> findByCategoryType(@RequestParam CategoryType type) {
        return findProductService.findProductByCategory(type);
    }

    @GetMapping("/in-stock")
    //    Пример запроса:   /api/products/in-stock?quantity=1
    public GeneralResponse<List<ProductResponseDto>> findOnlyInStock(@RequestParam Integer quantity) {
        return findProductService.findOnlyInStock(quantity);
    }

    @GetMapping("/status")
    //    Пример запроса:   /api/products/status?status=AVAILABLE
    public GeneralResponse<List<ProductResponseDto>> findByStatus(@RequestParam ProductStatus status) {
        return findProductService.findProductByStatus(status);
    }

    @DeleteMapping("/{id}")
    public GeneralResponse<ProductResponseDto> deleteProduct(@PathVariable Long id) {
        return deleteProductService.deleteProductById(id);
    }
}
