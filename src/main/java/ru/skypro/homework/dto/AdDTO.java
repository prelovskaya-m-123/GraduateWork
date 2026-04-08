package ru.skypro.homework.dto;

import lombok.Data;
import lombok.Builder;


@Data
@Builder
public class AdDTO {
    private Long pk;
    private Long author;
    private String title;
    private Integer price;
    private String image;
}
