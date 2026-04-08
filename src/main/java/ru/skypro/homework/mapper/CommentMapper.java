package ru.skypro.homework.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.CommentDTO;
import ru.skypro.homework.dto.CommentRequestDTO;
import ru.skypro.homework.model.Comment;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class CommentMapper {

    public CommentDTO toDto(Comment comment) {
        if (comment == null) {
            return null;
        }

        return CommentDTO.builder()
                .pk(comment.getPk())
                .author(comment.getAuthor() != null ? comment.getAuthor().getPk() : null)
                .authorImage(comment.getAuthorImage())
                .authorFirstName(comment.getAuthorFirstName())
                .createdAt(new Date(comment.getCreatedAt()))
                .text(comment.getText())
                .build();
    }

    public Comment toEntity(CommentRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        Comment comment = new Comment();
        comment.setText(dto.getText());
        return comment;
    }

    public void updateEntityFromDto(CommentRequestDTO dto, Comment comment) {
        if (dto == null || comment == null) {
            return;
        }
        comment.setText(dto.getText());
    }
}
