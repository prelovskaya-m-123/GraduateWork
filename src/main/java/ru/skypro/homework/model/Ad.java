package ru.skypro.homework.model;

import lombok.Data;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "ads")
@Data
public class Ad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pk;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    @Column(name = "title", nullable = false, length = 32)
    private String title;

    @Column(name = "price", nullable = false)
    private Integer price;

    @Column(name = "description", length = 64)
    private String description;

    @Column(name = "image_url")
    private String image;

    @OneToMany(mappedBy = "ad", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Comment> comments;
}
