package com.test.exercise.bookdepository.dto;

import com.test.exercise.bookdepository.model.Author;
import com.test.exercise.bookdepository.model.Genre;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {
    private LocalDate localDate;
    private int countPage;
    private Set<Author> authors;
    private Set<Genre> genres;
}
