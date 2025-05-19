package org.pchardwarestore.controller.accountController;

import lombok.RequiredArgsConstructor;

import org.pchardwarestore.service.FileStorageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class FileUploadController {

    private final FileStorageService service;

    @PostMapping("/api/upload")
    public ResponseEntity<String> fileUpload(@RequestParam("uploadFile")MultipartFile uploadFile){
        try{
            service.storeFile(uploadFile);
            return ResponseEntity.ok("Файл успешно загружен");
        } catch (Exception e){
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("Ошибка загрузки файла: {}" + e.getMessage());
        }
    }
}
