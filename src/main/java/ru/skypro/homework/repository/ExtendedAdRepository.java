package ru.skypro.homework.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.skypro.homework.model.Ad;

import java.util.Optional;

@Repository
public interface ExtendedAdRepository extends JpaRepository<Ad, Long> {

    /**
     * Получение объявления с загруженным автором (для ExtendedAdDTO)
     */
    @Query("SELECT a FROM Ad a " +
            "LEFT JOIN FETCH a.author " +
            "WHERE a.pk = :adId")
    Optional<Ad> findAdWithAuthor(Long adId);

    /**
     * Получение всех объявлений с авторами и пагинацией (сортировка по дате создания)
     */
    @Query("SELECT a FROM Ad a " +
            "LEFT JOIN FETCH a.author " +
            "ORDER BY a.createdAt DESC")
    Page<Ad> findAllWithAuthors(Pageable pageable);
}
