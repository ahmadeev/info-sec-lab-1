package ru.itmo.lab1.auth;

import java.nio.charset.StandardCharsets;

final class BcryptPasswordPolicy {

    private static final int MAXIMUM_PASSWORD_BYTES = 72;

    private BcryptPasswordPolicy() {
    }

    static boolean supports(String password) {
        return password != null
                && !password.isBlank()
                && password.getBytes(StandardCharsets.UTF_8).length <= MAXIMUM_PASSWORD_BYTES;
    }
}
