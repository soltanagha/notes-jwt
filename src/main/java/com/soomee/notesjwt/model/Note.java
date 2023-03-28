package com.soomee.notesjwt.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.HashSet;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "notes")
public class Note {
    @Id
    private String id;

    private String title;

    private String content;

    @CreatedDate
    private LocalDateTime createdOn;

    private HashSet<String> likedBy;

    private int countOfLike;

    @Version
    private Integer version;

}
