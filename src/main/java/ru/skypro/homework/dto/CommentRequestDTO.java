package ru.skypro.homework.dto;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class CommentRequestDTO {
    @NotBlank(message = "Текст комментария не может быть пустым")
    @Size(min = ValidationConstraints.COMMENT_TEXT_MIN_LENGTH,
            max = ValidationConstraints.COMMENT_TEXT_MAX_LENGTH,
            message = "Текст должен быть от " +
                    ValidationConstraints.COMMENT_TEXT_MIN_LENGTH +
                    " до " + ValidationConstraints.COMMENT_TEXT_MAX_LENGTH + " символов")
    private String text;
}
