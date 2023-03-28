package com.soomee.notesjwt.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoteDTO {

    private String id;

    private String title;

    @NotBlank(message = "Content is mandatory")
    private String content;

    private LocalDateTime createdOn;

    private int countOfLike;
}
