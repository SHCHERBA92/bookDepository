package com.test.exercise.bookdepository.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "genre")
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "genreIdSeq")
    @SequenceGenerator(name = "genreIdSeq", sequenceName = "GENRE_ID_SEQ")
    private Long id;

    @Column(nullable = true, name = "genre_name", unique = true)
    private String name;

    @OneToMany(mappedBy = "genre")
    private Set<Book> books;

    public Genre(String name, Set<Book> books) {
        this.name = name;
        this.books = books;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Genre genre = (Genre) o;
        return Objects.equals(id, genre.id)
                && Objects.equals(name, genre.name)
                && Objects.equals(books, genre.books);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, books);
    }
}
