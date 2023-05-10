package com.test.exercise.bookdepository.repository;

import com.test.exercise.bookdepository.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByTitle(String title);
    void deleteByTitle(String title);
}
