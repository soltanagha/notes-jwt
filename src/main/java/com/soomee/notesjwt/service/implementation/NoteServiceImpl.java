package com.soomee.notesjwt.service.implementation;

import com.soomee.notesjwt.dto.NoteDTO;
import com.soomee.notesjwt.model.Note;
import com.soomee.notesjwt.dto.exception.EmptyInputException;
import com.soomee.notesjwt.repository.NoteRepository;
import com.soomee.notesjwt.service.NoteService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;

    private final ModelMapper modelMapper;

    private static final Logger logger = LoggerFactory.getLogger(NoteServiceImpl.class);

    public NoteServiceImpl(NoteRepository noteRepository, ModelMapper modelMapper) {
        this.noteRepository = noteRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<NoteDTO> getAllNotes() {
        logger.info("Fetching all notes!");
        return noteRepository
                .findAllByOrderByCreatedOnDesc()
                .stream()
                .map(this::convertToNoteDto)
                .collect(Collectors.toList());
    }

    @Override
    public NoteDTO createNote(NoteDTO noteDTO) {
        logger.info("Creating new note in database!");
        if (noteDTO.getContent().isEmpty())
            throw new EmptyInputException("601","Note content is empty!");
        else if (noteDTO.getComments() == null)
            noteDTO.setComments(new ArrayList<>());
        Note note = convertToNoteEntity(noteDTO);
        note.setLikedBy(new HashSet<>());
        note.setCountOfLike(0);
        noteDTO = convertToNoteDto(noteRepository.save(note));

        return noteDTO;
    }

    @Override
    public NoteDTO updateNote(NoteDTO noteDTO) {
        logger.info("Update note!");
        return noteRepository.findById(noteDTO.getId()).stream().map((note) -> {
            note.setTitle(noteDTO.getTitle());
            note.setContent(noteDTO.getContent());
            noteRepository.save(note);
            return convertToNoteDto(note);
        }).findFirst().orElseThrow(NoSuchElementException::new);
    }


    @Override
    public NoteDTO likeNote(String noteId, String userName) {
        logger.info("Like note by id: "+noteId);
        Note note = noteRepository.findById(noteId).
                orElseThrow(() -> new NoSuchElementException("Note not found id: " + noteId));

        HashSet<String> likes = note.getLikedBy();
        likes.add(userName);
        note.setLikedBy(likes);
        note.setCountOfLike(note.getLikedBy().size());
        noteRepository.save(note);
        return convertToNoteDto(note);
    }

    @Override
    public NoteDTO unlikeNote(String noteId, String userName) {
        logger.info("Unlike note by id: "+noteId);
        Note note = noteRepository.findById(noteId).
                orElseThrow(() -> new UsernameNotFoundException("Note not found id: " + noteId));

        HashSet<String> likes = note.getLikedBy();
        likes.remove(userName);
        note.setLikedBy(likes);
        note.setCountOfLike(note.getLikedBy().size());
        noteRepository.save(note);
        return convertToNoteDto(note);
    }

    @Override
    public NoteDTO deleteNoteByID(String id) {
        logger.info("Delete note by id: "+id);
        Note note = noteRepository.findById(id).stream()
                .findFirst().orElseThrow(NoSuchElementException::new);
        noteRepository.delete(note);
        return convertToNoteDto(note);
    }

    private NoteDTO convertToNoteDto(Note note) {
        return modelMapper.map(note, NoteDTO.class);
    }

    private Note convertToNoteEntity(NoteDTO noteDTO) {
        return modelMapper.map(noteDTO, Note.class);
    }

}
