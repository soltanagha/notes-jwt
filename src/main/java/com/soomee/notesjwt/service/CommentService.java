package com.soomee.notesjwt.service;

import com.soomee.notesjwt.dto.CommentDTO;

import java.util.List;

public interface CommentService {

    List<CommentDTO> getAllComments();

    CommentDTO addComment(CommentDTO commentDTO);

    CommentDTO getCommentById(String id);

    CommentDTO updateCommentById(String id, CommentDTO commentDTO);

    CommentDTO deleteCommentById(String id);

    CommentDTO likeComment(String commentId, String userName);

    CommentDTO unlikeComment(String commentId, String userName);
}
