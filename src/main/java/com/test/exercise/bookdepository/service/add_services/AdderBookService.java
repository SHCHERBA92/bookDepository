package com.test.exercise.bookdepository.service.add_services;

import com.test.exercise.bookdepository.dto.BookDTO;
import com.test.exercise.bookdepository.exception.BookException;
import com.test.exercise.bookdepository.model.Author;
import com.test.exercise.bookdepository.model.Book;
import com.test.exercise.bookdepository.repository.AuthorRepository;
import com.test.exercise.bookdepository.repository.BookRepository;
import com.test.exercise.bookdepository.repository.GenreRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional(isolation = Isolation.REPEATABLE_READ,
        propagation = Propagation.REQUIRES_NEW,
        rollbackFor = {SQLException.class})
public class AdderBookService implements AddEntity<BookDTO> {

    private final static Logger LOGGER = LogManager.getLogger(AdderBookService.class);

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final ModelMapper modelMapper;

    public AdderBookService(BookRepository bookRepository,
                            AuthorRepository authorRepository,
                            GenreRepository genreRepository,
                            ModelMapper modelMapper) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
        this.modelMapper = modelMapper;
    }

    /**
     * Метод добавления сущности книги в БД + добавление Жанра БД(если есть) + добавление Автора(если есть).
     *
     * @param bookDTO JSON объект книга
     */
    @Override
    public void add(BookDTO bookDTO) {
        if (bookDTO == null) {
            LOGGER.error("Не удалось добавить книгу в БД, по причине некорректного JSON");
            throw new BookException("Книга не задана");
        }
        Book book = modelMapper.map(bookDTO, Book.class);

        String genreName = book.getGenre().getName();
        genreRepository.findByName(genreName).ifPresent(genre -> book.setGenre(genre));

        Set<Author> authors = book.getAuthors();
        Set<Author> newAuthors = new HashSet<>();
        for (Author author : authors) {
            authorRepository.findAuthorByNameAndSureName(author.getName(), author.getSureName())
                    .ifPresentOrElse(auth -> newAuthors.add(auth), () -> newAuthors.add(author));
        }
        book.setAuthors(newAuthors);
        bookRepository.saveAndFlush(book);
        LOGGER.info("Книга был добавлен успешно");
    }

    /**
     * Метод добавления списка сущностей книга в БД + добавление Жанра БД(если есть) + добавление Автора(если есть).
     *
     * @param bookDTOList массив JSON объектов книга
     */
    @Override
    public void addAll(List<BookDTO> bookDTOList) {
        if (bookDTOList == null || bookDTOList.isEmpty()) throw new BookException("Список книг не передан");
        List<Book> bookList = bookDTOList.stream()
                .map(bookDTO -> modelMapper.map(bookDTO, Book.class))
                .collect(Collectors.toList());
        bookRepository.saveAllAndFlush(bookList);
    }
}
