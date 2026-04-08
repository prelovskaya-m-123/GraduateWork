package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdDTO;
import ru.skypro.homework.dto.AdsDTO;
import ru.skypro.homework.dto.AdRequestDTO;
import ru.skypro.homework.dto.ExtendedAdDTO;
import ru.skypro.homework.exception.AdNotFoundException;
import ru.skypro.homework.exception.UsernameNotFoundException;
import ru.skypro.homework.mapper.AdMapper;
import ru.skypro.homework.mapper.ComplexMapper;
import ru.skypro.homework.model.Ad;
import ru.skypro.homework.model.User;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.AdService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdServiceImpl implements AdService {

    private final AdRepository adRepository;
    private final UserRepository userRepository;
    private final AdMapper adMapper;
    private final ComplexMapper complexMapper;

    private static final String IMAGE_UPLOAD_DIR = "uploads/images/";

    @Override
    public AdsDTO getAllAds() {
        List<Ad> ads = adRepository.findAll();
        return complexMapper.toAdsDto(ads);
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public AdsDTO getMyAds() {
        var currentUser = getCurrentUser();
        List<Ad> myAds = adRepository.findByAuthorPk(currentUser.getPk());
        return complexMapper.toAdsDto(myAds);
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public AdDTO createAd(AdRequestDTO properties, MultipartFile image) throws IOException {
        var currentUser = getCurrentUser();

        Ad ad = adMapper.toEntity(properties);
        ad.setAuthor(currentUser);

        String imageUrl = saveImage(image);
        ad.setImage(imageUrl);

        Ad savedAd = adRepository.save(ad);
        return adMapper.toDto(savedAd);
    }

    @Override
    public ExtendedAdDTO getExtendedAd(Long id) {
        Ad ad = adRepository.findAdWithAuthor(id)
                .orElseThrow(() -> new AdNotFoundException("Объявление не найдено"));
        return complexMapper.toExtendedAdDto(ad);
    }

    @Override
    @PreAuthorize("@adService.isAdOwner(#id)")
    public AdDTO updateAd(Long id, AdRequestDTO request) {
        Ad existingAd = adRepository.findById(id)
                .orElseThrow(() -> new AdNotFoundException("Объявление не найдено"));

        adMapper.updateEntityFromDto(request, existingAd);
        Ad updatedAd = adRepository.save(existingAd);

        return adMapper.toDto(updatedAd);
    }

    @Override
    @PreAuthorize("@adService.isAdOwner(#id)")
    public void deleteAd(Long id) {
        if (!adRepository.existsById(id)) {
            throw new AdNotFoundException("Объявление не найдено");
        }
        adRepository.deleteById(id);
    }

    @Override
    @PreAuthorize("@adService.isAdOwner(#id)")
    public void updateAdImage(Long id, MultipartFile image) throws IOException {
        Ad ad = adRepository.findById(id)
                .orElseThrow(() -> new AdNotFoundException("Объявление не найдено"));

        String imageUrl = saveImage(image);
        ad.setImage(imageUrl);
        adRepository.save(ad);
    }

    @Override
    public boolean isAdOwner(Long adId) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        return adRepository.findById(adId)
                .map(ad -> ad.getAuthor().getEmail().equals(currentUsername))
                .orElse(false);
    }

    @Override
    public boolean isAdAccessibleForUser(Long adId) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        return adRepository.findById(adId)
                .map(ad -> {
                    return ad.getAuthor().getEmail().equals(currentUsername) ||
                            authentication.getAuthorities().stream()
                                    .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
                })
                .orElse(false);
    }

    /**
     * Вспомогательный метод для сохранения изображения
     * @param image загружаемое изображение
     * @return URL или путь к сохранённому изображению
     */
    private String saveImage(MultipartFile image) throws IOException {
        Path uploadDir = Paths.get(IMAGE_UPLOAD_DIR);
        Files.createDirectories(uploadDir);

        String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
        Path filePath = uploadDir.resolve(fileName);

        image.transferTo(filePath.toFile());

        return IMAGE_UPLOAD_DIR + fileName;
    }

    /**
     * Получаем текущего пользователя из контекста безопасности
     * @return сущность пользователя
     */
    private User getCurrentUser() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        return userRepository.findByEmail(currentUsername)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
    }
}
