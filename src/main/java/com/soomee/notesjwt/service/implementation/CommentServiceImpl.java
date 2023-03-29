package com.soomee.notesjwt.service.implementation;

import com.soomee.notesjwt.dto.CommentDTO;
import com.soomee.notesjwt.model.Comment;
import com.soomee.notesjwt.model.Note;
import com.soomee.notesjwt.model.exception.EmptyInputException;
import com.soomee.notesjwt.repository.CommentRepository;
import com.soomee.notesjwt.repository.NoteRepository;
import com.soomee.notesjwt.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final NoteRepository noteRepository;
    private final ModelMapper modelMapper;

    public CommentServiceImpl(CommentRepository commentRepository, NoteRepository noteRepository, ModelMapper modelMapper) {
        this.commentRepository = commentRepository;
        this.noteRepository = noteRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public List<CommentDTO> getAllComments() {
        return commentRepository
                .findAll()
                .stream()
                .map(this::convertToCommentDto)
                .collect(Collectors.toList());
    }

    @Override
    public CommentDTO addComment(CommentDTO commentDTO) {
        if (commentDTO.getContent().isEmpty() || commentDTO.getNoteID().isEmpty())
            throw new EmptyInputException("601","Comment content or note ID is empty!");

        Note note = noteRepository.findById(commentDTO.getNoteID()).orElseThrow(NoSuchElementException::new);
        Comment newComment = convertToCommentEntity(commentDTO);
        newComment.setLikedBy(new HashSet<>());
        newComment.setCountOfLike(0);
        newComment.setNoteId(note.getId());
        newComment = commentRepository.save(newComment);

        return convertToCommentDto(newComment);
    }

    @Override
    public CommentDTO getCommentById(String id) {
        return commentRepository.findById(id)
                .stream()
                .map(this::convertToCommentDto)
                .findFirst().orElseThrow(NoSuchElementException::new);
    }

    @Override
    public CommentDTO updateCommentById(String id, CommentDTO commentDTO) {
        return commentRepository.findById(id).stream().map((comment) -> {
            comment.setContent(commentDTO.getContent());
            commentRepository.save(comment);
            return convertToCommentDto(comment);
        }).findFirst().orElseThrow(NoSuchElementException::new);
    }

    @Override
    public CommentDTO deleteCommentById(String id) {
        Comment comment = commentRepository.findById(id).stream()
                .findFirst().orElseThrow(NoSuchElementException::new);
        commentRepository.delete(comment);
        return convertToCommentDto(comment);
    }

    @Override
    public CommentDTO likeComment(String commentId, String userName) {
        Comment comment = commentRepository.findById(commentId).
                orElseThrow(() -> new NoSuchElementException("Note not found id: " + commentId));

        HashSet<String> likes = comment.getLikedBy();
        likes.add(userName);
        comment.setLikedBy(likes);
        comment.setCountOfLike(comment.getLikedBy().size());
        commentRepository.save(comment);
        return convertToCommentDto(comment);
    }

    @Override
    public CommentDTO unlikeComment(String commentId, String userName) {
        Comment comment = commentRepository.findById(commentId).
                orElseThrow(() -> new NoSuchElementException("Note not found id: " + commentId));

        HashSet<String> likes = comment.getLikedBy();
        likes.remove(userName);
        comment.setLikedBy(likes);
        comment.setCountOfLike(comment.getLikedBy().size());
        commentRepository.save(comment);
        return convertToCommentDto(comment);
    }

    private CommentDTO convertToCommentDto(Comment comment) {
        return modelMapper.map(comment, CommentDTO.class);
    }

    private Comment convertToCommentEntity(CommentDTO commentDTO) {
        return modelMapper.map(commentDTO, Comment.class);
    }

}
