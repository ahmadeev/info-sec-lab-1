package ru.itmo.lab1.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import ru.itmo.lab1.common.validation.Utf8ByteLength;
import ru.itmo.lab1.common.validation.ValidationLimits;

public record LoginRequest(
        @NotBlank(message = "must not be blank")
        @Size(max = ValidationLimits.LOGIN_MAX_LENGTH, message = "must not exceed 100 characters")
        String login,

        @NotBlank(message = "must not be blank")
        @Utf8ByteLength(max = ValidationLimits.PASSWORD_MAX_UTF8_BYTES)
        String password
) {
}
