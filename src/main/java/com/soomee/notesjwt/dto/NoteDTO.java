package com.soomee.notesjwt.dto;

import com.soomee.notesjwt.model.Comment;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoteDTO {

    private String id;

    @NotBlank
    private String title;

    @NotBlank(message = "Content is mandatory")
    private String content;

    private List<Comment> comments;

    private LocalDateTime createdOn;

    private int countOfLike;
}
