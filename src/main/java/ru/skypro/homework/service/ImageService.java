package ru.skypro.homework.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class ImageService {

    @Value("${app.upload.dir}")
    private String uploadDir;

    public String saveImage(MultipartFile image, String subdirectory) throws IOException {
        Path basePath = Paths.get(uploadDir);
        Path subPath = basePath.resolve(subdirectory);
        Files.createDirectories(subPath);

        String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
        Path filePath = subPath.resolve(fileName);

        image.transferTo(filePath.toFile());

        return subdirectory + "/" + fileName;
    }

    public byte[] getImage(String imagePath) throws IOException {
        Path filePath = Paths.get(uploadDir, imagePath);
        return Files.readAllBytes(filePath);
    }
}
