package ru.itmo.lab1.note;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itmo.lab1.common.HtmlEscaper;
import ru.itmo.lab1.user.AppUser;
import ru.itmo.lab1.user.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoteService {

    private final NoteRepository noteRepository;
    private final UserRepository userRepository;
    private final HtmlEscaper htmlEscaper;

    @Transactional(readOnly = true)
    public List<NoteResponse> findAll(String ownerLogin) {
        AppUser owner = findOwner(ownerLogin);

        return noteRepository.findAllByOwnerIdOrderByIdAsc(owner.getId()).stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public NoteResponse create(String ownerLogin, CreateNoteRequest request) {
        AppUser owner = findOwner(ownerLogin);
        Note note = noteRepository.save(new Note(owner, request.title(), request.content()));

        return toResponse(note);
    }

    private AppUser findOwner(String login) {
        return userRepository.findByLogin(login)
                .orElseThrow(CurrentUserNotFoundException::new);
    }

    private NoteResponse toResponse(Note note) {
        return new NoteResponse(
                note.getId(),
                htmlEscaper.escape(note.getTitle()),
                htmlEscaper.escape(note.getContent())
        );
    }
}
