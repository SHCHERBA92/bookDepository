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
//TODO: перенести оставшиеся методы
public class BookService {
    public final BookRepository bookRepository;
    public final ModelMapper modelMapper;

    public BookService(BookRepository bookRepository,
                       ModelMapper modelMapper) {
        this.bookRepository = bookRepository;
        this.modelMapper = modelMapper;
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
