package com.test.exercise.bookdepository.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
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
@JsonRootName("book")
public class BookDTO {
    @JsonProperty("title")
    private String title;

    @JsonProperty("publication_date")
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "dd.MM.yyyy"
    )
    private LocalDate localDate;

    @JsonProperty("count_page")
    private int countPage;

    @JsonProperty("author")
    private Set<AuthorDTO> authors;

    @JsonProperty("genre")
    private GenreDTO genre;
}
