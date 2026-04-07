package ru.skypro.homework.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UsernameNotFoundException extends ResponseStatusException {
    public UsernameNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
