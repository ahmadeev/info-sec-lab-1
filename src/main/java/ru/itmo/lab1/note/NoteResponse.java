package ru.itmo.lab1.note;

public record NoteResponse(
        Long id,
        String title,
        String content
) {
}
