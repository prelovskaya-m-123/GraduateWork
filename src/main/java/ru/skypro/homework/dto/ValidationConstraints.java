package ru.skypro.homework.dto;

public class ValidationConstraints {
    // Для объявлений
    public static final int TITLE_MIN_LENGTH = 4;
    public static final int TITLE_MAX_LENGTH = 32;
    public static final int DESCRIPTION_MIN_LENGTH = 8;
    public static final int DESCRIPTION_MAX_LENGTH = 64;

    // Для комментариев
    public static final int COMMENT_TEXT_MIN_LENGTH = 8;
    public static final int COMMENT_TEXT_MAX_LENGTH = 64;

    // Для пользователей
    public static final int FIRST_NAME_MIN_LENGTH = 2;
    public static final int FIRST_NAME_MAX_LENGTH = 10;
    public static final int LAST_NAME_MIN_LENGTH = 2;
    public static final int LAST_NAME_MAX_LENGTH = 10;

    // Для логина
    public static final int USERNAME_MIN_LENGTH = 4;
    public static final int USERNAME_MAX_LENGTH = 32;

    // Для паролей
    public static final int PASSWORD_MIN_LENGTH = 8;
    public static final int PASSWORD_MAX_LENGTH = 16;
}
