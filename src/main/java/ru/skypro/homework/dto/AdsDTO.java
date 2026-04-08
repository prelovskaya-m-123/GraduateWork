package ru.skypro.homework.dto;

import lombok.Data;
import lombok.Builder;

import java.util.List;

@Data
@Builder
public class AdsDTO {
    private Integer count;
    private List<AdDTO> results;
}
