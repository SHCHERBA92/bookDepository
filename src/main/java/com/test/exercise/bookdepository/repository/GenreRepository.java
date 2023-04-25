package com.test.exercise.bookdepository.repository;

import com.test.exercise.bookdepository.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}
