package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.CommentDTO;
import ru.skypro.homework.dto.CommentRequestDTO;
import ru.skypro.homework.dto.CommentsDTO;
import ru.skypro.homework.exception.CommentNotFoundException;
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
    @PreAuthorize("hasRole('ADMIN') or " +
            "(@commentService.isCommentOwner(#commentId) and " +
            "@adService.isAdAccessibleForUser(#adId))")
    public ResponseEntity<?> deleteComment(
            @PathVariable Long adId,
            @PathVariable Long commentId) {

        try {
            commentService.deleteComment(adId, commentId);
            return ResponseEntity.noContent().build();
        } catch (CommentNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (AccessDeniedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @PatchMapping("/{commentId}")
    @PreAuthorize("hasRole('ADMIN') or " +
            "(@commentService.isCommentOwner(#commentId) and " +
            "@adService.isAdAccessibleForUser(#adId))")
    public ResponseEntity<CommentDTO> updateComment(
            @PathVariable Long adId,
            @PathVariable Long commentId,
            @Valid @RequestBody CommentRequestDTO request,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        try {
            CommentDTO updatedComment = commentService.updateComment(adId, commentId, request);
            return ResponseEntity.ok(updatedComment);
        } catch (CommentNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (AccessDeniedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

}
