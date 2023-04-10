package com.soomee.notesjwt;

import com.soomee.notesjwt.controller.NoteController;
import com.soomee.notesjwt.dto.NoteDTO;
import com.soomee.notesjwt.dto.response.Response;
import com.soomee.notesjwt.service.NoteService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class NoteControllerTest {
    @Mock
    private NoteService noteService;

    @InjectMocks
    private NoteController noteController;

    @Test
    @DisplayName("Test findAllNotes endpoint")
    @WithMockUser(roles = "USER")
    void testFindAllNotes() {
        NoteDTO note1 = NoteDTO.builder().title("Title 1").content("Content 1").build();
        NoteDTO note2 = NoteDTO.builder().title("Title 2").content("Content 2").build();
        List<NoteDTO> notes = Arrays.asList(note1, note2);
        given(noteService.getAllNotes()).willReturn(notes);

        ResponseEntity<Response> responseEntity = noteController.findAllNotes();

        assertEquals(responseEntity.getStatusCode(),HttpStatus.OK);
        assertEquals(Objects.requireNonNull(responseEntity.getBody()).getData().get("notes"),notes);
    }

    @Test
    @DisplayName("Test create endpoint")
    @WithMockUser(roles = "MANAGER")
    void testCreate() {
        NoteDTO noteDTO = NoteDTO.builder().title("Title DTO").content("Content DTO").build();
        given(noteService.createNote(noteDTO)).willReturn(noteDTO);

        ResponseEntity<Response> responseEntity = noteController.create(noteDTO);

        assertEquals(responseEntity.getStatusCode(),HttpStatus.OK);
        assertEquals(Objects.requireNonNull(responseEntity.getBody()).getData().get("note"),noteDTO);
    }

    @Test
    @DisplayName("Test update endpoint")
    @WithMockUser(roles = "MANAGER")
    void testUpdate() {
        NoteDTO noteDTO = NoteDTO.builder().title("Title TEST").content("Content TEST").build();
        given(noteService.updateNote(noteDTO)).willReturn(noteDTO);

        ResponseEntity<Response> responseEntity = noteController.update(noteDTO);

        assertEquals(responseEntity.getStatusCode(),HttpStatus.OK);
        assertEquals(Objects.requireNonNull(responseEntity.getBody()).getData().get("note"),noteDTO);
    }

    @Test
    @DisplayName("Test delete endpoint")
    void testDelete() {
        String noteId = "123";
        NoteDTO noteDTO = NoteDTO.builder().title("Title TEST").content("Content TEST").build();
        given(noteService.deleteNoteByID(noteId)).willReturn(noteDTO);

        ResponseEntity<Response> responseEntity = noteController.delete(noteId);

        assertEquals(responseEntity.getStatusCode(),HttpStatus.OK);
        assertEquals(Objects.requireNonNull(responseEntity.getBody()).getData().get("note"),noteDTO);
    }

    @Test
    @DisplayName("Test likeNote endpoint")
    @WithMockUser(roles = "MANAGER")
    void testLikeNote() {
        String noteId = "123";
        String username = "user";
        NoteDTO noteDTO = NoteDTO.builder().title("Title TEST").content("Content TEST").build();
        given(noteService.likeNote(noteId, username)).willReturn(noteDTO);

        ResponseEntity<Response> responseEntity = noteController.likeNote(noteId, new User(username, "", new ArrayList<>()));

        assertEquals(responseEntity.getStatusCode(),HttpStatus.OK);
        assertEquals(Objects.requireNonNull(responseEntity.getBody()).getData().get("note"),noteDTO);
    }

    @Test
    @DisplayName("Test unlikeNote endpoint")
    @WithMockUser(roles = "MANAGER")
    void testUnlikeNote() {
        String noteId = "123";
        String username = "user";
        NoteDTO noteDTO = NoteDTO.builder().title("Title TEST").content("Content TEST").build();
        given(noteService.unlikeNote(noteId, username)).willReturn(noteDTO);

        ResponseEntity<Response> responseEntity = noteController.unlikeNote(noteId, new User(username, "", new ArrayList<>()));

        assertEquals(responseEntity.getStatusCode(),HttpStatus.OK);
        assertEquals(Objects.requireNonNull(responseEntity.getBody()).getData().get("note"),noteDTO);
    }
}