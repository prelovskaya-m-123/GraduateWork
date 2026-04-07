package ru.skypro.homework.model;

import lombok.Data;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
@Data
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pk;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ad_id", nullable = false)
    private Ad ad;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    @Column(name = "text", nullable = false, length = 64)
    private String text;

    @Column(name = "created_at", nullable = false)
    private Long createdAt;

    @Column(name = "author_image")
    private String authorImage;

    @Column(name = "author_first_name")
    private String authorFirstName;
}
