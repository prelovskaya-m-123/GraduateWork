package ru.skypro.homework.dto;

import lombok.Data;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class UpdateUserDTO {
    @Size(min = ValidationConstraints.FIRST_NAME_MIN_LENGTH,
            max = ValidationConstraints.FIRST_NAME_MAX_LENGTH,
            message = "Имя должно быть от " +
                    ValidationConstraints.FIRST_NAME_MIN_LENGTH +
                    " до " + ValidationConstraints.FIRST_NAME_MAX_LENGTH + " символов")
    private String firstName;

    @Size(min = ValidationConstraints.LAST_NAME_MIN_LENGTH,
            max = ValidationConstraints.LAST_NAME_MAX_LENGTH,
            message = "Фамилия должна быть от " +
                    ValidationConstraints.LAST_NAME_MIN_LENGTH +
                    " до " + ValidationConstraints.LAST_NAME_MAX_LENGTH + " символов")
    private String lastName;

    @Pattern(regexp = "\\+7\\s?\\(?\\d{3}\\)?\\s?\\d{3}-?\\d{2}-?\\d{2}",
            message = "Неверный формат телефона")
    private String phone;
}