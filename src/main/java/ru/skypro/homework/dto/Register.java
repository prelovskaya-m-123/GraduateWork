package ru.skypro.homework.dto;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class Register {
    @NotBlank(message = "Логин не может быть пустым")
    @Size(min = ValidationConstraints.USERNAME_MIN_LENGTH,
            max = ValidationConstraints.USERNAME_MAX_LENGTH,
            message = "Логин должен быть от " +
                    ValidationConstraints.USERNAME_MIN_LENGTH +
                    " до " + ValidationConstraints.USERNAME_MAX_LENGTH + " символов")
    private String username;

    @NotBlank(message = "Пароль не может быть пустым")
    @Size(min = ValidationConstraints.PASSWORD_MIN_LENGTH,
            max = ValidationConstraints.PASSWORD_MAX_LENGTH,
            message = "Пароль должен быть от " +
                    ValidationConstraints.PASSWORD_MIN_LENGTH +
                    " до " + ValidationConstraints.PASSWORD_MAX_LENGTH + " символов")
    private String password;

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

    private String phone;
    private Role role;
}
