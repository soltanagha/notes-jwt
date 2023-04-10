package com.soomee.notesjwt.service.implementation;

import com.soomee.notesjwt.dto.CommentDTO;
import com.soomee.notesjwt.model.Comment;
import com.soomee.notesjwt.model.Note;
import com.soomee.notesjwt.dto.exception.EmptyInputException;
import com.soomee.notesjwt.repository.CommentRepository;
import com.soomee.notesjwt.repository.NoteRepository;
import com.soomee.notesjwt.service.CommentService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(CommentServiceImpl.class);


    public CommentServiceImpl(CommentRepository commentRepository, NoteRepository noteRepository, ModelMapper modelMapper) {
        this.commentRepository = commentRepository;
        this.noteRepository = noteRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public List<CommentDTO> getAllComments() {
        logger.info("Fetching all comments from database!");
        return commentRepository
                .findAll()
                .stream()
                .map(this::convertToCommentDto)
                .collect(Collectors.toList());
    }

    @Override
    public CommentDTO addComment(CommentDTO commentDTO) {
        logger.info("Creating new comment in database!");
        if (commentDTO.getContent().isEmpty() || commentDTO.getNoteID().isEmpty())
            throw new EmptyInputException("601","Comment content or note ID is empty!");

        logger.info("Sent comment is valid!");
        Note note = noteRepository.findById(commentDTO.getNoteID()).orElseThrow(NoSuchElementException::new);
        Comment newComment = convertToCommentEntity(commentDTO);
        newComment.setLikedBy(new HashSet<>());
        newComment.setCountOfLike(0);
        newComment.setNoteId(note.getId());
        newComment = commentRepository.save(newComment);

        logger.info("New comment successfully added and note updated!");

        return convertToCommentDto(newComment);
    }

    @Override
    public CommentDTO getCommentById(String id) {
        logger.info("Fetching comment by id: "+id);
        Comment comment = commentRepository.findById(id).stream()
                .findFirst().orElseThrow(NoSuchElementException::new);

        return convertToCommentDto(comment);
    }

    @Override
    public CommentDTO updateCommentById(String id, CommentDTO commentDTO) {
        logger.info("Update comment by id: "+id);
        return commentRepository.findById(id).stream().map((comment) -> {
            comment.setContent(commentDTO.getContent());
            commentRepository.save(comment);
            return convertToCommentDto(comment);
        }).findFirst().orElseThrow(NoSuchElementException::new);
    }

    @Override
    public CommentDTO deleteCommentById(String id) {
        logger.info("Delete comment by id: "+id);
        Comment comment = commentRepository.findById(id).stream()
                .findFirst().orElseThrow(NoSuchElementException::new);
        commentRepository.delete(comment);
        return convertToCommentDto(comment);
    }

    @Override
    public CommentDTO likeComment(String commentId, String userName) {
        logger.info("Like comment by id: "+commentId);

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
        logger.info("Unlike comment by id: "+commentId);

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
