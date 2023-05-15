package com.test.exercise.bookdepository.repository;

import com.test.exercise.bookdepository.model.Author;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    Optional<Author> findAuthorByNameAndSureName(String authorName, String sureName);
    Optional<Author> findAuthorBySureName(String sureName);
}
