package org.pchardwarestore.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;

import org.pchardwarestore.entity.accountEntity.FileInfo;
import org.pchardwarestore.repository.accountRepository.FileInfoRepository;
import org.pchardwarestore.service.accountService.userService.FindUserService;
import org.pchardwarestore.service.accountService.userService.RegistrationUserService;
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
public class FileStorageService {

    private final FileInfoRepository repository;
    private final FindUserService findUserService;
    private final Path FileStorageLocation = Paths.get("src/main/resources/static/upload");

    //todo--------------------------------
    public String storeFile(MultipartFile file){
        String filename = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            Path targetFile = FileStorageLocation.resolve(filename);
            Files.copy(file.getInputStream(), targetFile, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e){
            throw new RuntimeException("Ошибка сохранения файла: " + filename);
        }

        // Путь для клиента (не файловой системы)
        String link = "/upload/" + filename;

        FileInfo fileInfo = new FileInfo();
        fileInfo.setLink(link);
        fileInfo.setUser(findUserService.getCurrentUser());

        repository.save(fileInfo);
        return "Файл " + link + " успешно сохранен";
    }
    //todo--------------------------------

//    public String storeFile(MultipartFile file){
//
//        String filename = StringUtils.cleanPath(file.getOriginalFilename());
//
//        // убирает (очищает) из имени файла "ошибочные" символы (например: ../document.pdf")
//
//        try{
//
//            Path targetFile = FileStorageLocation.resolve(filename);
//
//            Files.copy(file.getInputStream(), targetFile, StandardCopyOption.REPLACE_EXISTING);
//        } catch (IOException e){
//            throw new RuntimeException("Ошибка сохранения файла: " + filename);
//        }
//        String link = FilenameUtils.concat(FileStorageLocation.toString(), filename);
//        FileInfo fileInfo = new FileInfo();
//        fileInfo.setLink(link);
//        fileInfo.setUser(findUserService.getCurrentUser());
//
//        repository.save(fileInfo);
//        return "Файл " + link + " успешно сохранен";
//    }
}
