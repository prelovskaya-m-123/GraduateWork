package ru.skypro.homework.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.homework.model.Ad;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdRepository extends JpaRepository<Ad, Long> {

    /**
     * Получение объявлений пользователя
     */
    List<Ad> findByAuthorPk(Long authorId);

    /**
     * Поиск объявления по ID
     */
    Optional<Ad> findById(Long id);

    /**
     * Получение всех объявлений с пагинацией
     */
    Page<Ad> findAll(Pageable pageable);

    /**
     * Проверка принадлежности объявления пользователю
     */
    boolean existsByIdAndAuthorPk(Long adId, Long authorId);
}
