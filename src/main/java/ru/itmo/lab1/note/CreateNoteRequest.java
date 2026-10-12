package ru.itmo.lab1.note;

import jakarta.validation.constraints.NotBlank;

public record CreateNoteRequest(
        @NotBlank String title,
        @NotBlank String content
) {
}
