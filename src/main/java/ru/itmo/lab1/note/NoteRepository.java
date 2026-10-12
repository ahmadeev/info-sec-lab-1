package ru.itmo.lab1.note;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Long> {

    List<Note> findAllByOwnerIdOrderByIdAsc(Long ownerId);
}
