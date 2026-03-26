package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.CommentDTO;
import ru.skypro.homework.dto.CommentRequestDTO;
import ru.skypro.homework.dto.CommentsDTO;
import ru.skypro.homework.service.CommentService;

import javax.validation.Valid;

@RestController
@RequestMapping("/ads/{adId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping
    public ResponseEntity<CommentsDTO> getComments(@PathVariable Long adId) {
        return ResponseEntity.ok(commentService.getComments(adId));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<CommentDTO> addComment(
            @PathVariable Long adId,
            @Valid @RequestBody CommentRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(commentService.addComment(adId, request));
    }

    @DeleteMapping("/{commentId}")
    @PreAuthorize("hasRole('ADMIN') or @commentService.isCommentOwner(#commentId)")
    public ResponseEntity<?> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{commentId}")
    @PreAuthorize("hasRole('ADMIN') or @commentService.isCommentOwner(#commentId)")
    public ResponseEntity<CommentDTO> updateComment(
            @PathVariable Long commentId,
            @Valid @RequestBody CommentRequestDTO request) {
        return ResponseEntity.ok(commentService.updateComment(commentId, request));
    }
}
