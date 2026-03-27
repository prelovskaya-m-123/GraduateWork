package ru.skypro.homework.dto;

import lombok.Data;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class AdRequestDTO {
    @NotBlank(message = "Заголовок не может быть пустым")
    @Size(min = ValidationConstraints.TITLE_MIN_LENGTH,
            max = ValidationConstraints.TITLE_MAX_LENGTH,
            message = "Заголовок должен быть от " +
                    ValidationConstraints.TITLE_MIN_LENGTH +
                    " до " + ValidationConstraints.TITLE_MAX_LENGTH + " символов")
    private String title;

    @Min(value = 0, message = "Цена не может быть отрицательной")
    private Integer price;

    @NotBlank(message = "Описание не может быть пустым")
    @Size(min = ValidationConstraints.DESCRIPTION_MIN_LENGTH,
            max = ValidationConstraints.DESCRIPTION_MAX_LENGTH,
            message = "Описание должно быть от " +
                    ValidationConstraints.DESCRIPTION_MIN_LENGTH +
                    " до " + ValidationConstraints.DESCRIPTION_MAX_LENGTH + " символов")
    private String description;
}