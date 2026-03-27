package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdDTO;
import ru.skypro.homework.dto.AdsDTO;
import ru.skypro.homework.dto.AdRequestDTO;
import ru.skypro.homework.dto.ExtendedAdDTO;
import ru.skypro.homework.service.AdService;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/ads")
@RequiredArgsConstructor
public class AdController {

    private final AdService adService;

    @GetMapping
    public ResponseEntity<AdsDTO> getAllAds() {
        return ResponseEntity.ok(adService.getAllAds());
    }

    @GetMapping("/me")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<AdsDTO> getMyAds() {
        return ResponseEntity.ok(adService.getMyAds());
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<AdDTO> addAd(
            @Valid @RequestPart("properties") AdRequestDTO properties,
            @RequestPart MultipartFile image) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(adService.createAd(properties, image));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExtendedAdDTO> getAd(@PathVariable Long id) {
        return ResponseEntity.ok(adService.getExtendedAd(id));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @adService.isAdOwner(#id)")
    public ResponseEntity<?> deleteAd(@PathVariable Long id) {
        adService.deleteAd(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @adService.isAdOwner(#id)")
    public ResponseEntity<AdDTO> updateAd(
            @PathVariable Long id,
            @Valid @RequestBody AdRequestDTO request) {
        return ResponseEntity.ok(adService.updateAd(id, request));
    }

    @PatchMapping("/{id}/image")
    @PreAuthorize("hasRole('ADMIN') or @adService.isAdOwner(#id)")
    public ResponseEntity<byte[]> updateAdImage(
            @PathVariable Long id,
            @RequestPart MultipartFile image) throws IOException {
        byte[] imageBytes = adService.updateAdImage(id, image);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(imageBytes);
    }

}
