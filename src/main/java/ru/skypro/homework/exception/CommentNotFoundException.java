package ru.skypro.homework.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class CommentNotFoundException extends ResponseStatusException {
    public CommentNotFoundException() {
        super(HttpStatus.NOT_FOUND, "Комментарий не найден");
    }

    public CommentNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
