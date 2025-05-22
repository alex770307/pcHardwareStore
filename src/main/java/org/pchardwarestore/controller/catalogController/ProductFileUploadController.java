package org.pchardwarestore.controller.catalogController;

import lombok.RequiredArgsConstructor;
import org.pchardwarestore.service.ProductFileService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class ProductFileUploadController {

    private final ProductFileService productFileService;

    @PostMapping("/api/products/{id}/upload-photo")
    public ResponseEntity<String> uploadProductPhoto(@PathVariable Long id,
                                                     @RequestParam("file") MultipartFile file) {
        String responseMessage = productFileService.storeFile(file, id);
        return ResponseEntity.ok(responseMessage);
    }
}
