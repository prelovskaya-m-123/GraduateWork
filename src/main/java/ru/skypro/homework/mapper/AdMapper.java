package ru.skypro.homework.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.AdDTO;
import ru.skypro.homework.dto.AdRequestDTO;
import ru.skypro.homework.model.Ad;

@Component
@RequiredArgsConstructor
public class AdMapper {

    public AdDTO toDto(Ad ad) {
        if (ad == null) {
            return null;
        }

        return AdDTO.builder()
                .pk(ad.getPk())
                .author(ad.getAuthor() != null ? ad.getAuthor().getPk() : null)
                .title(ad.getTitle())
                .price(ad.getPrice())
                .image(ad.getImage())
                .build();
    }

    public Ad toEntity(AdRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        Ad ad = new Ad();
        ad.setTitle(dto.getTitle());
        ad.setPrice(dto.getPrice());
        ad.setDescription(dto.getDescription());
        return ad;
    }

    public void updateEntityFromDto(AdRequestDTO dto, Ad ad) {
        if (dto == null || ad == null) {
            return;
        }
        ad.setTitle(dto.getTitle());
        ad.setPrice(dto.getPrice());
        ad.setDescription(dto.getDescription());
    }
}
