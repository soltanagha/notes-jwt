package com.soomee.notesjwt.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.HashSet;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Comment {

    @Id
    private String id;
    private String content;

    ObjectId noteId;

    private int countOfLike;

    private HashSet<String> likedBy;

    @CreatedDate
    private LocalDateTime createdOn;

    @Version
    private Integer version;
}
