package com.soomee.notesjwt.controller;

import com.soomee.notesjwt.dto.CommentDTO;
import com.soomee.notesjwt.dto.response.Response;
import com.soomee.notesjwt.service.implementation.CommentServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import static java.time.LocalDateTime.now;
import static java.util.Map.of;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/comment")
public class CommentController {

    private final CommentServiceImpl commentService;

    public CommentController(CommentServiceImpl commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    public ResponseEntity<Response> getAllComments() {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(of("comments",commentService.getAllComments()))
                        .message("Comment fetched all")
                        .status(OK)
                        .statusCode(OK.value())
                        .build());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('MANAGER') or hasRole('ADMIN')")
    public ResponseEntity<Response> getComment(@PathVariable("id") String id) {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(of("comment",commentService.getCommentById(id)))
                        .message("Comment fetched by ID")
                        .status(OK)
                        .statusCode(OK.value())
                        .build());
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Response> addComment(@Valid @RequestBody CommentDTO commentDTO) {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(of("comment",commentService.addComment(commentDTO)))
                        .message("Comment successfully added!")
                        .status(OK)
                        .statusCode(OK.value())
                        .build());
    }


    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Response> updateComment(@PathVariable("id") String id,@Valid @RequestBody CommentDTO commentDTO) {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(of("comment",commentService.updateCommentById(id,commentDTO)))
                        .message("Comment successfully updated!")
                        .status(OK)
                        .statusCode(OK.value())
                        .build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<Response> deleteComment(@PathVariable("id") String id) {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(of("comment",commentService.deleteCommentById(id)))
                        .message("Comment successfully deleted!")
                        .status(OK)
                        .statusCode(OK.value())
                        .build());
    }

    @PostMapping("/like/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Response> likeComment(@PathVariable("id") String id, @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(of("comment",commentService.likeComment(id, userDetails.getUsername())))
                        .message("Comment successfully liked!")
                        .status(OK)
                        .statusCode(OK.value())
                        .build());
    }

    @PostMapping("/unlike/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Response> unlikeComment(@PathVariable("id") String id, @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(of("comment",commentService.unlikeComment(id, userDetails.getUsername())))
                        .message("Comment successfully unliked!")
                        .status(OK)
                        .statusCode(OK.value())
                        .build());
    }

}
