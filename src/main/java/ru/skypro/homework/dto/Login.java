package ru.skypro.homework.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class Login {
    @NotBlank(message = "Логин не может быть пустым")
    private String username;

    @NotBlank(message = "Пароль не может быть пустым")
    private String password;
}
