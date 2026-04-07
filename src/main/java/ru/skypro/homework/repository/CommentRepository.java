package ru.skypro.homework.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.skypro.homework.model.Comment;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    Page<Comment> findByAdPkOrderByCreatedAtDesc(Long adId, Pageable pageable);

    List<Comment> findByAdPkAndAuthorPk(Long adId, Long authorId);

    boolean existsByIdAndAdPkAndAuthorPk(Long commentId, Long adId, Long authorId);

    boolean existsByIdAndAuthorPk(Long commentId, Long authorId);

    long countByAdPk(Long adId);

    @Query("SELECT c FROM Comment c WHERE c.ad.pk = :adId ORDER BY c.createdAt DESC")
    List<Comment> findTopCommentsByAdPk(@Param("adId") Long adId, Pageable pageable);

    void deleteByAdPk(Long adId);

    boolean existsByIdAndAdPk(Long commentId, Long adId);
}
