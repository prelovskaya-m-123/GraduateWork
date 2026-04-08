package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
        AdsDTO ads = adService.getAllAds();
        return ResponseEntity.ok(ads);
    }

    @GetMapping("/me")
    public ResponseEntity<AdsDTO> getMyAds() {
        AdsDTO myAds = adService.getMyAds();
        return ResponseEntity.ok(myAds);
    }

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<AdDTO> createAd(
            @Valid @ModelAttribute AdRequestDTO properties,
            @RequestParam("image") MultipartFile image) throws IOException {
        AdDTO createdAd = adService.createAd(properties, image);
        return ResponseEntity.status(201).body(createdAd);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExtendedAdDTO> getExtendedAd(@PathVariable Long id) {
        ExtendedAdDTO ad = adService.getExtendedAd(id);
        return ResponseEntity.ok(ad);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AdDTO> updateAd(
            @PathVariable Long id,
            @Valid @RequestBody AdRequestDTO request) {
        if (!adService.isAdOwner(id)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        AdDTO updatedAd = adService.updateAd(id, request);
        return ResponseEntity.ok(updatedAd);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAd(@PathVariable Long id) {
        if (!adService.isAdOwner(id)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        adService.deleteAd(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/image")
    public ResponseEntity<byte[]> updateAdImage(
            @PathVariable Long id,
            @RequestParam("image") MultipartFile image) throws IOException {
        byte[] imageBytes = adService.updateAdImage(id, image);
        return ResponseEntity.ok()
                .header("Content-Type", "application/octet-stream")
                .body(imageBytes);
    }
}
