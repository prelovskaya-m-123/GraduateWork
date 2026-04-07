package ru.skypro.homework.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.ExtendedAdDTO;
import ru.skypro.homework.model.Ad;

@Component
@RequiredArgsConstructor
public class ExtendedAdMapper {

    public ExtendedAdDTO toDto(Ad ad) {
        if (ad == null) {
            return null;
        }

        ExtendedAdDTO.ExtendedAdDTOBuilder builder = ExtendedAdDTO.builder()
                .pk(ad.getPk())
                .title(ad.getTitle())
                .price(ad.getPrice())
                .description(ad.getDescription())
                .image(ad.getImage());

        if (ad.getAuthor() != null) {
            builder.authorFirstName(ad.getAuthor().getFirstName())
                    .authorLastName(ad.getAuthor().getLastName())
                    .email(ad.getAuthor().getEmail())
                    .phone(ad.getAuthor().getPhone());
        }

        return builder.build();
    }
}
