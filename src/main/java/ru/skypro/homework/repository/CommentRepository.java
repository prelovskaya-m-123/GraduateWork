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

    /**
     * Получение комментариев к объявлению с пагинацией и сортировкой по дате создания (новые сверху)
     */
    Page<Comment> findByAdPkOrderByCreatedAtDesc(Long adId, Pageable pageable);

    /**
     * Получение комментариев к объявлению конкретного автора
     */
    List<Comment> findByAdPkAndAuthorPk(Long adId, Long authorId);

    /**
     * Проверка существования комментария с указанием объявления и автора
     */
    boolean existsByIdAndAdPkAndAuthorPk(Long commentId, Long adId, Long authorId);

    /**
     * Проверка принадлежности комментария пользователю
     */
    boolean existsByIdAndAuthorPk(Long commentId, Long authorId);

    /**
     * Подсчёт количества комментариев к объявлению
     */
    long countByAdPk(Long adId);

    /**
     * Получение последних N комментариев к объявлению (например, последних 5)
     */
    @Query("SELECT c FROM Comment c WHERE c.ad.pk = :adId ORDER BY c.createdAt DESC LIMIT :limit")
    List<Comment> findTopCommentsByAdPk(@Param("adId") Long adId, @Param("limit") int limit);

    /**
     * Удаление всех комментариев к объявлению (используется при удалении объявления)
     */
    void deleteByAdPk(Long adId);
}
