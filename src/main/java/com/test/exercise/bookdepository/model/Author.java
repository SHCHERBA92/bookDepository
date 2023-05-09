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
@Table(name = "author")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
}
