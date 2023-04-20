package com.test.exercise.bookdepository.repository;

import com.test.exercise.bookdepository.models.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
