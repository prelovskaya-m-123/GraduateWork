package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.skypro.homework.model.Ad;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdRepository extends JpaRepository<Ad, Long> {

    List<Ad> findAll();

    Optional<Ad> findById(Long id);

    List<Ad> findByAuthorPk(Long authorId);

    boolean existsByIdAndAuthorPk(Long adId, Long authorId);

    @Query("SELECT a FROM Ad a LEFT JOIN FETCH a.author WHERE a.pk = :adId")
    Optional<Ad> findAdWithAuthor(@Param("adId") Long adId);

    @Query("SELECT a FROM Ad a LEFT JOIN FETCH a.author ORDER BY a.createdAt DESC")
    List<Ad> findAllWithAuthors();

}
