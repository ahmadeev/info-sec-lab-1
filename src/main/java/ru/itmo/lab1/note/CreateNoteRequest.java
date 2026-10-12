package ru.itmo.lab1.note;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import ru.itmo.lab1.common.validation.ValidationLimits;

public record CreateNoteRequest(
        @NotBlank(message = "must not be blank")
        @Size(max = ValidationLimits.TITLE_MAX_LENGTH, message = "must not exceed 200 characters")
        String title,

        @NotBlank(message = "must not be blank")
        @Size(max = ValidationLimits.CONTENT_MAX_LENGTH, message = "must not exceed 5000 characters")
        String content
) {
}
