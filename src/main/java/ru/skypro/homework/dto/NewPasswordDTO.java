package ru.skypro.homework.dto;

import lombok.Data;
import javax.validation.constraints.Size;

@Data
public class NewPasswordDTO {
    @Size(min = ValidationConstraints.PASSWORD_MIN_LENGTH,
            max = ValidationConstraints.PASSWORD_MAX_LENGTH,
            message = "Текущий пароль должен быть от " +
                    ValidationConstraints.PASSWORD_MIN_LENGTH +
                    " до " + ValidationConstraints.PASSWORD_MAX_LENGTH + " символов")
    private String currentPassword;

    @Size(min = ValidationConstraints.PASSWORD_MIN_LENGTH,
            max = ValidationConstraints.PASSWORD_MAX_LENGTH,
            message = "Новый пароль должен быть от " +
                    ValidationConstraints.PASSWORD_MIN_LENGTH +
                    " до " + ValidationConstraints.PASSWORD_MAX_LENGTH + " символов")
    private String newPassword;
}