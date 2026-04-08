package ru.skypro.homework.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdDTO;
import ru.skypro.homework.dto.AdRequestDTO;
import ru.skypro.homework.dto.AdsDTO;
import ru.skypro.homework.dto.ExtendedAdDTO;
import ru.skypro.homework.model.User;

import java.io.IOException;

public interface AdService {

    AdsDTO getAllAds();

    AdsDTO getMyAds();

    AdDTO createAd(AdRequestDTO properties, MultipartFile image) throws IOException;

    ExtendedAdDTO getExtendedAd(Long id);

    AdDTO updateAd(Long id, AdRequestDTO request);

    void deleteAd(Long id);

    byte[] updateAdImage(Long id, MultipartFile image) throws IOException;

    boolean isAdOwner(Long adId);

    boolean isAdAccessibleForUser(Long adId);
}
