package com.test.exercise.bookdepository.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "book")
/**
 * Делаем сущность Book главной сущностью для JPA,
 * добавление списка Авторов и Жанра в эту сущность будет вести за собой их запись(обновление) в БД.
 */
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false, unique = false)
    private String title;

    @Column(name = "publisher_date", nullable = false, unique = false, columnDefinition = "DATE")
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private LocalDate localDate;

    @Column(name = "pages", nullable = false, unique = false)
    private int countPage;

    @ManyToMany( cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(name = "book_author",
        joinColumns = @JoinColumn(name = "book_id"),
        inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private Set<Author> authors;

    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "genre_id")
    private Genre genre;
}
