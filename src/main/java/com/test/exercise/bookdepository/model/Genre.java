package com.test.exercise.bookdepository.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
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
}
