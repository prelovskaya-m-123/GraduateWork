package ru.skypro.homework.dto;

import lombok.Data;
import java.util.List;
import lombok.Builder;


@Data
@Builder
public class CommentsDTO {
    private Integer count;
    private List<CommentDTO> results;
}
