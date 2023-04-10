package com.soomee.notesjwt.dto;

import com.soomee.notesjwt.model.User;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentDTO {

    private String id;
    @NotBlank
    private String content;

    @NotBlank
    private String noteID;

    private int countOfLike;

    private HashSet<String> likedBy;
    private User createdBy;
}
