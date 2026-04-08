package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.skypro.homework.service.ImageService;

import java.io.IOException;

@RestController
@RequestMapping("/images")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @GetMapping(value = "/{subdir}/{filename:.+}",
            produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_GIF_VALUE})
    public ResponseEntity<byte[]> getImage(
            @PathVariable String subdir,
            @PathVariable String filename) {

        try {
            String imagePath = subdir + "/" + filename;
            byte[] imageBytes = imageService.getImage(imagePath);
            return ResponseEntity.ok()
                    .contentType(MediaType.valueOf(getContentType(filename)))
                    .body(imageBytes);
        } catch (IOException e) {
            return ResponseEntity.notFound().build();
        }
    }

    private String getContentType(String filename) {
        String lowerCaseName = filename.toLowerCase();
        if (lowerCaseName.endsWith(".png")) return MediaType.IMAGE_PNG_VALUE;
        if (lowerCaseName.endsWith(".jpg") || lowerCaseName.endsWith(".jpeg")) return MediaType.IMAGE_JPEG_VALUE;
        if (lowerCaseName.endsWith(".gif")) return MediaType.IMAGE_GIF_VALUE;
        return MediaType.APPLICATION_OCTET_STREAM_VALUE;
    }
}

