package ru.skypro.homework.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.skypro.homework.dto.CommentDTO;
import ru.skypro.homework.dto.CommentsDTO;
import ru.skypro.homework.dto.CommentRequestDTO;
import ru.skypro.homework.exception.AdNotFoundException;
import ru.skypro.homework.exception.CommentNotFoundException;
import ru.skypro.homework.mapper.ComplexMapper;
import ru.skypro.homework.model.Ad;
import ru.skypro.homework.model.Comment;
import ru.skypro.homework.model.User;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.mapper.CommentMapper;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.skypro.homework.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final AdRepository adRepository;
    private final UserRepository userRepository;
    private final CommentMapper commentMapper;
    private final ComplexMapper complexMapper;

    public CommentsDTO getComments(Long adId) {
        validateAdExists(adId);
        List<Comment> comments = commentRepository.findByAdPkOrderByCreatedAtDesc(adId, Pageable.unpaged()).getContent();
        return complexMapper.toCommentsDto(comments);
    }

    public CommentDTO addComment(Long adId, CommentRequestDTO request) {
        validateAdExists(adId);

        var currentUser = getCurrentUser();
        Ad ad = adRepository.findById(adId)
                .orElseThrow(() -> new AdNotFoundException("Объявление не найдено"));

        Comment comment = commentMapper.toEntity(request);
        comment.setAd(ad);
        comment.setAuthor(currentUser);
        comment.setCreatedAt(System.currentTimeMillis());
        comment.setAuthorImage(currentUser.getImage());
        comment.setAuthorFirstName(currentUser.getFirstName());

        Comment savedComment = commentRepository.save(comment);
        return commentMapper.toDto(savedComment);
    }

    @PreAuthorize("@commentService.isCommentOwner(#commentId)")
    public CommentDTO updateComment(Long adId, Long commentId, CommentRequestDTO request) {
        validateAdExists(adId);

        Comment existingComment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException("Комментарий не найден"));

        commentMapper.updateEntityFromDto(request, existingComment);
        Comment updatedComment = commentRepository.save(existingComment);

        return commentMapper.toDto(updatedComment);
    }

    @PreAuthorize("@commentService.isCommentOwner(#commentId)")
    public void deleteComment(Long adId, Long commentId) {
        validateAdExists(adId);

        if (!commentRepository.existsByIdAndAdPk(commentId, adId)) {
            throw new CommentNotFoundException("Комментарий не найден");
        }
        commentRepository.deleteById(commentId);
    }

    private void validateAdExists(Long adId) {
        if (!adRepository.existsById(adId)) {
            throw new AdNotFoundException("Объявление не найдено");
        }
    }

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        return userRepository.findByEmail(currentUsername)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
    }

    public boolean isCommentOwner(Long commentId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        return commentRepository.findById(commentId)
                .map(comment -> comment.getAuthor().getEmail().equals(currentUsername))
                .orElse(false);
    }
}

