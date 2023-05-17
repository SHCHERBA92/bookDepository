package com.test.exercise.bookdepository.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;
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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bookIdSeq")
    @SequenceGenerator(name = "bookIdSeq", sequenceName = "BOOK_ID_SEQ")
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

    public Book(String title, LocalDate localDate, int countPage, Set<Author> authors, Genre genre) {
        this.title = title;
        this.localDate = localDate;
        this.countPage = countPage;
        this.authors = authors;
        this.genre = genre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return countPage == book.countPage
                && Objects.equals(id, book.id)
                && Objects.equals(title, book.title)
                && Objects.equals(localDate, book.localDate)
                && Objects.equals(authors, book.authors)
                && Objects.equals(genre, book.genre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, localDate, countPage, authors, genre);
    }
}
