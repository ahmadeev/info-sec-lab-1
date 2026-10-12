package ru.itmo.lab1.common.validation;

public final class ValidationLimits {

    public static final int LOGIN_MAX_LENGTH = 100;
    public static final int PASSWORD_MAX_UTF8_BYTES = 72;
    public static final int TITLE_MAX_LENGTH = 200;
    public static final int CONTENT_MAX_LENGTH = 5000;

    private ValidationLimits() {
    }
}
