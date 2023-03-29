package com.soomee.notesjwt.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "notes")
public class Note {
    @Id
    private ObjectId id;

    private String title;

    private String content;

    @CreatedDate
    private LocalDateTime createdOn;

    private HashSet<String> likedBy;

    private int countOfLike;

    @ReadOnlyProperty
    @DocumentReference(lookup = "{ 'noteId' :?#{#self._id} }")
    public List<Comment> comments = new ArrayList<>();

    @Version
    private Integer version;

}
