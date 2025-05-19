package org.pchardwarestore.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;

import org.pchardwarestore.entity.accountEntity.FileInfo;
import org.pchardwarestore.entity.accountEntity.User;
import org.pchardwarestore.repository.accountRepository.FileInfoRepository;
import org.pchardwarestore.service.accountService.userService.FindUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    @Transactional
    public String storeFile(MultipartFile file) {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            Path targetFile = FileStorageLocation.resolve(filename);
            Files.copy(file.getInputStream(), targetFile, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка сохранения файла: " + filename);
        }

        String link = "/upload/" + filename;

        User currentUser = findUserService.getCurrentUser();

        FileInfo fileInfo = new FileInfo();
        fileInfo.setLink(link);
        fileInfo.setUser(currentUser);
        repository.save(fileInfo);

        if (currentUser.getPhotoLink() == null || currentUser.getPhotoLink().isBlank()) {
            currentUser.setPhotoLink(link);
        }

//        currentUser.getPhotos().add(fileInfo);

        return "Файл " + link + " успешно сохранен";
    }
}

//    public String storeFile(MultipartFile file) {
//        String filename = StringUtils.cleanPath(file.getOriginalFilename());
//
//        try {
//            Path targetFile = FileStorageLocation.resolve(filename);
//            Files.copy(file.getInputStream(), targetFile, StandardCopyOption.REPLACE_EXISTING);
//        } catch (IOException e) {
//            throw new RuntimeException("Ошибка сохранения файла: " + filename);
//        }
//
//        String link = "/upload/" + filename;
//
//        FileInfo fileInfo = new FileInfo();
//        fileInfo.setLink(link);
//        fileInfo.setUser(findUserService.getCurrentUser());
//
//        repository.save(fileInfo);
//        return "Файл " + link + " успешно сохранен";
//    }
//}
