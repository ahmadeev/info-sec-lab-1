package ru.itmo.lab1.auth;

import java.nio.charset.StandardCharsets;

import static ru.itmo.lab1.common.validation.ValidationLimits.PASSWORD_MAX_UTF8_BYTES;

final class BcryptPasswordPolicy {

    private BcryptPasswordPolicy() {
    }

    static boolean supports(String password) {
        return password != null
                && !password.isBlank()
                && password.getBytes(StandardCharsets.UTF_8).length <= PASSWORD_MAX_UTF8_BYTES;
    }
}
