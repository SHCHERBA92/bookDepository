package com.test.exercise.bookdepository.service.add_services;

import com.test.exercise.bookdepository.dto.BookDTO;
import com.test.exercise.bookdepository.exception.BookException;
import com.test.exercise.bookdepository.model.Author;
import com.test.exercise.bookdepository.model.Book;
import com.test.exercise.bookdepository.model.Genre;
import com.test.exercise.bookdepository.repository.AuthorRepository;
import com.test.exercise.bookdepository.repository.BookRepository;
import com.test.exercise.bookdepository.repository.GenreRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

//TODO: добавить логирование
@Service
public class AdderBookService implements AddEntity<BookDTO>{


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
        if (bookDTO == null) throw new BookException("Книга не задана");
        Book book = modelMapper.map(bookDTO, Book.class);

        String genreName = book.getGenre().getName();
        genreRepository.findByName(genreName).ifPresent(genre -> book.setGenre(genre));

        Set<Author> authors = book.getAuthors();
        Set<Author> newAuthors = new HashSet<>();
        for (Author author : authors) {
            authorRepository.findAuthorByNameAndSureName(author.getName(), author.getSureName())
                    .ifPresentOrElse(auth ->newAuthors.add(auth), () -> newAuthors.add(author));
        }
        book.setAuthors(newAuthors);
        bookRepository.saveAndFlush(book);
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
