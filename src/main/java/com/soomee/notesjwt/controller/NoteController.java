package com.soomee.notesjwt.controller;

import com.soomee.notesjwt.dto.NoteDTO;
import com.soomee.notesjwt.dto.response.Response;
import com.soomee.notesjwt.service.NoteService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import static java.time.LocalDateTime.now;
import static java.util.Map.of;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/notes")
public class NoteController {
    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Response> findAllNotes() {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(of("notes",noteService.getAllNotes()))
                        .message("Fetched all notes!")
                        .status(OK)
                        .statusCode(OK.value())
                        .build());
    }

    @PostMapping
    @PreAuthorize("hasRole('MANAGER') or hasRole('ADMIN')")
    public ResponseEntity<Response> create(@Valid @RequestBody NoteDTO note) {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(of("note",noteService.createNote(note)))
                        .message("Saved new Note!")
                        .status(OK)
                        .statusCode(OK.value())
                        .build());
    }

    @PutMapping
    @PreAuthorize("hasRole('MANAGER') or hasRole('ADMIN')")
    public ResponseEntity<Response> update(@Valid @RequestBody NoteDTO noteDTO) {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(of("note",noteService.updateNote(noteDTO)))
                        .message("Note successfully updated!")
                        .status(OK)
                        .statusCode(OK.value())
                        .build());
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<Response> delete(@PathVariable("id") String id) {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(of("note",noteService.deleteNoteByID(id)))
                        .message("Note successfully deleted!")
                        .status(OK)
                        .statusCode(OK.value())
                        .build());
    }

    @PostMapping("/like/{noteId}")
    @PreAuthorize("hasRole('MANAGER') or hasRole('ADMIN')")
    public ResponseEntity<Response> likeNote(@PathVariable String noteId, @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(of("note",noteService.likeNote(noteId, userDetails.getUsername())))
                        .message("Note liked!")
                        .status(OK)
                        .statusCode(OK.value())
                        .build());
    }

    @PostMapping("/unlike/{noteId}")
    @PreAuthorize("hasRole('MANAGER') or hasRole('ADMIN')")
    public ResponseEntity<Response> unlikeNote(@PathVariable String noteId, @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(of("note",noteService.unlikeNote(noteId, userDetails.getUsername())))
                        .message("Note unliked!")
                        .status(OK)
                        .statusCode(OK.value())
                        .build());
    }

}
