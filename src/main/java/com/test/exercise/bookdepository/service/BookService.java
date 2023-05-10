package com.test.exercise.bookdepository.service;

import com.test.exercise.bookdepository.dto.BookDTO;
import com.test.exercise.bookdepository.exception.BookException;
import com.test.exercise.bookdepository.model.Book;
import com.test.exercise.bookdepository.repository.BookRepository;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
//TODO: добавить логирование
public class BookService {
    public final BookRepository bookRepository;
    public final ModelMapper modelMapper;

    public BookService(BookRepository bookRepository,
                       ModelMapper modelMapper) {
        this.bookRepository = bookRepository;
        this.modelMapper = modelMapper;
    }

    /**
     * Метод добавления сущности книги в БД + добавление Жанра БД(если есть) + добавление Автора(если есть).
     *
     * @param bookDTO JSON объект книга
     */
    public void addBook(BookDTO bookDTO) {
        if (bookDTO == null) throw new BookException("Книга не задана");
        Book book = modelMapper.map(bookDTO, Book.class);
        bookRepository.saveAndFlush(book);
    }

    /**
     * Метод добавления списка сущностей книга в БД + добавление Жанра БД(если есть) + добавление Автора(если есть).
     *
     * @param bookDTOList массив JSON объектов книга
     */
    public void addBooks(Set<BookDTO> bookDTOList) {
        if (bookDTOList == null || bookDTOList.isEmpty()) throw new BookException("Список книг не передан");
        List<Book> bookList = bookDTOList.stream()
                .map(bookDTO -> modelMapper.map(bookDTO, Book.class))
                .collect(Collectors.toList());
        bookRepository.saveAllAndFlush(bookList);
    }

    /**
     * Метод который находит сущность книгу по идентификатору
     *
     * @param id идентификатор книги
     * @return транспортную сущность книги
     */
    public BookDTO getBook(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new BookException("Книги с идентификатором " + id + " не удалось найти"));
        return modelMapper.map(book, BookDTO.class);
    }

    /**
     * Метод который находит сущность книгу по названию
     *
     * @param title название книги
     * @return транспортную сущность книга
     */
    public BookDTO getBook(String title) {
        Book book = bookRepository.findByTitle(title)
                .orElseThrow(() -> new BookException(String.format("Не удалось найти книгу с названием %s", title)));
        return modelMapper.map(book, BookDTO.class);
    }

    /**
     * Метод возвращает все книги
     *
     * @return список книг
     */
    public List<BookDTO> getAllBook() {
        List<Book> all = bookRepository.findAll();
        if (all == null || all.isEmpty()) throw new BookException(String.format("Список книг пуст"));
        return all.stream()
                .map(book -> modelMapper.map(book, BookDTO.class))
                .collect(Collectors.toList());
    }

    /**
     * Метод который изменяет поля сущности книги по её названию
     *
     * @param bookDTO JSON сущность книги для изменения
     */
    public void changeBook(BookDTO bookDTO){
        if (bookDTO == null) throw new BookException("Книга не задана");
        Book inputBook = modelMapper.map(bookDTO, Book.class);
        Book book = bookRepository.findByTitle(bookDTO.getTitle())
                .orElse(inputBook);
        Long bookId = book.getId();
        if (bookId ==null){
            bookRepository.saveAndFlush(book);
            //TODO: добавить логирование
        }else {
            inputBook.setId(bookId);
            bookRepository.saveAndFlush(inputBook);
        }
    }

    /**
     * Удаление книги по её идентификатору
     *
     * @param id идентификатор книги
     */
    public void deleteBook(Long id){
        if (id==null) throw new BookException("Не задан идентификатор книги");
        try {
            bookRepository.deleteById(id);
        }catch (RuntimeException e){
            throw new BookException("Не удалось удалить книгу");
        }
    }

    /**
     * Удаление книги по её названию
     *
     * @param title название книги
     */
    public void deleteBook(String title){
        if (StringUtils.isBlank(title)) throw new BookException("Не введено название книги");
        try {
            bookRepository.deleteByTitle(title);
        }catch (RuntimeException e){
            throw new BookException(String.format("Не удалось удалить книгу под названием %s", title));
        }
    }
}
