package ru.skypro.homework.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.model.*;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ComplexMapper {

    private final UserMapper userMapper;
    private final AdMapper adMapper;
    private final CommentMapper commentMapper;
    private final ExtendedAdMapper extendedAdMapper;

    public AdsDTO toAdsDto(List<Ad> ads) {
        return AdsDTO.builder()
                .count(ads.size())
                .results(ads.stream()
                        .map(adMapper::toDto)
                        .collect(Collectors.toList()))
                .build();
    }

    public ExtendedAdDTO toExtendedAdDto(Ad ad) {
        return extendedAdMapper.toDto(ad);
    }

    public CommentsDTO toCommentsDto(List<Comment> comments) {
        return CommentsDTO.builder()
                .count(comments.size())
                .results(comments.stream()
                        .map(commentMapper::toDto)
                        .collect(Collectors.toList()))
                .build();
    }
}
