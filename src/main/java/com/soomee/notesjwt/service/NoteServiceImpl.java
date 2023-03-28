package com.soomee.notesjwt.service;

import com.soomee.notesjwt.dto.NoteDTO;
import com.soomee.notesjwt.model.Note;
import com.soomee.notesjwt.model.exception.EmptyInputException;
import com.soomee.notesjwt.repository.NoteRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;

    private final ModelMapper modelMapper;

    public NoteServiceImpl(NoteRepository noteRepository, ModelMapper modelMapper) {
        this.noteRepository = noteRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<NoteDTO> getAllNotes() {
        return noteRepository
                .findAllByOrderByCreatedOnDesc()
                .stream()
                .map(this::convertToNoteDto)
                .collect(Collectors.toList());
    }

    @Override
    public NoteDTO createNote(NoteDTO noteDTO) {
        if (noteDTO.getContent().isEmpty())
            throw new EmptyInputException("601","Note content is empty!");

        Note note = convertToNoteEntity(noteDTO);
        note.setLikedBy(new HashSet<>());
        note.setCountOfLike(0);
        noteDTO = convertToNoteDto(noteRepository.save(note));
        return noteDTO;
    }

    @Override
    public NoteDTO likeNote(String noteId, String userName) {
        Note note = noteRepository.findById(noteId).
                orElseThrow(() -> new UsernameNotFoundException("Note not found id: " + noteId));

        HashSet<String> likes = note.getLikedBy();
        likes.add(userName);
        note.setLikedBy(likes);
        note.setCountOfLike(note.getLikedBy().size());
        noteRepository.save(note);
        return convertToNoteDto(note);
    }

    @Override
    public NoteDTO unlikeNote(String noteId, String userName) {
        Note note = noteRepository.findById(noteId).
                orElseThrow(() -> new UsernameNotFoundException("Note not found id: " + noteId));

        HashSet<String> likes = note.getLikedBy();
        likes.remove(userName);
        note.setLikedBy(likes);
        note.setCountOfLike(note.getLikedBy().size());
        noteRepository.save(note);
        return convertToNoteDto(note);
    }

    private NoteDTO convertToNoteDto(Note note) {
        return modelMapper.map(note, NoteDTO.class);
    }

    private Note convertToNoteEntity(NoteDTO noteDTO) {
        return modelMapper.map(noteDTO, Note.class);
    }

}
