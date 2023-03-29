package com.soomee.notesjwt.dto;

import com.soomee.notesjwt.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {

    private String id;
    private String content;

    private String noteID;

    private int countOfLike;

    private HashSet<String> likedBy;
    private User createdBy;
}
