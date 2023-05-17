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
@Table(name = "author")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "authorIdSeq")
    @SequenceGenerator(name = "authorIdSeq", sequenceName = "AUTHOR_ID_SEQ")
    private Long id;

    @Column(name = "name", unique = false, nullable = false)
    private String name;

    @Column(name = "sure_name", unique = false, nullable = false)
    private String sureName;

    @Column(name = "birth_date", unique = false, nullable = true, columnDefinition = "DATE")
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private LocalDate localDate;

    @ManyToMany(mappedBy = "authors")
    private Set<Book> books;

    public Author(String name, String sureName, LocalDate localDate, Set<Book> books) {
        this.name = name;
        this.sureName = sureName;
        this.localDate = localDate;
        this.books = books;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return Objects.equals(id, author.id)
                && Objects.equals(name, author.name)
                && Objects.equals(sureName, author.sureName)
                && Objects.equals(localDate, author.localDate)
                && Objects.equals(books, author.books);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, sureName, localDate, books);
    }
}
