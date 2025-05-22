package org.pchardwarestore.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.pchardwarestore.entity.catalogEntity.Product;
import org.pchardwarestore.entity.catalogEntity.ProductPhoto;
import org.pchardwarestore.repository.catalogRepository.ProductRepository;
import org.pchardwarestore.service.catalogService.productService.FindProductService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
@RequiredArgsConstructor
public class ProductFileService {

    private final ProductRepository productRepository;
    private final FindProductService findProductService;
    private final Path fileStorageLocation = Paths.get("src/main/resources/static/upload/product_img");

    @Transactional
    public String storeFile(MultipartFile file, Long productId) {
        Product product = findProductService.getCurrentProduct(productId);

        String filename = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            Path targetFile = fileStorageLocation.resolve(filename);
            Files.copy(file.getInputStream(), targetFile, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Error saving file: " + filename);
        }

        String link = "/product_img/" + filename;

        ProductPhoto photo = new ProductPhoto();
        photo.setLink(link);
        photo.setProduct(product);

        if (product.getPhotoLink() == null || product.getPhotoLink().isBlank()) {
            product.setPhotoLink(link);
        }

        product.getPhotos().add(photo);
        productRepository.save(product);

        return "Файл " + link + " успешно сохранен";
    }
}
