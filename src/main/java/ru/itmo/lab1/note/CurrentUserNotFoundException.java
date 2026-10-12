package ru.itmo.lab1.note;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class CurrentUserNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public CurrentUserNotFoundException() {
        super("Authenticated user does not exist");
    }
}
