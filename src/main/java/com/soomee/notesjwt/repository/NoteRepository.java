package com.soomee.notesjwt.repository;

import com.soomee.notesjwt.model.Note;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface NoteRepository extends MongoRepository<Note,String> {
    List<Note> findAllByOrderByCreatedOnDesc();
}
