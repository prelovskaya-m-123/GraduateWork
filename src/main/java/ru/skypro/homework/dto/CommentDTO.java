package ru.skypro.homework.dto;

import lombok.Data;
import java.util.Date;
import lombok.Builder;

@Builder
@Data
public class CommentDTO {
    private Long pk;
    private Long author;
    private String authorImage;
    private String authorFirstName;
    private Date createdAt;
    private String text;
}
