package com.soomee.notesjwt.service;


import com.soomee.notesjwt.dto.NoteDTO;

import java.util.List;

public interface NoteService {


    List<NoteDTO> getAllNotes();

    NoteDTO createNote(NoteDTO note);


    NoteDTO likeNote(String noteId, String userName);

    NoteDTO unlikeNote(String noteId, String userName);
}
