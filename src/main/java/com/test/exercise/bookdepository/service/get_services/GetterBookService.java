package com.test.exercise.bookdepository.service.get_services;

import com.test.exercise.bookdepository.dto.BookDTO;
import com.test.exercise.bookdepository.exception.BookException;
import com.test.exercise.bookdepository.model.Book;
import com.test.exercise.bookdepository.repository.BookRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

//TODO: добавить логирование
@Service
@Transactional(readOnly = true)
public class GetterBookService implements GetEntity<BookDTO>{
    public final BookRepository bookRepository;
    public final ModelMapper modelMapper;

    public GetterBookService(BookRepository bookRepository,
                             ModelMapper modelMapper) {
        this.bookRepository = bookRepository;
        this.modelMapper = modelMapper;
    }

    /**
     * Метод который находит сущность книгу по идентификатору
     *
     * @param id идентификатор книги
     * @return транспортную сущность книги
     */
    @Override
    public BookDTO getById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new BookException("Книги с идентификатором " + id + " не удалось найти"));
        return modelMapper.map(book, BookDTO.class);
    }

    /**
     * Метод который находит сущность книгу по названию
     *
     * @param title название книги
     * @return транспортную сущность книга
     */
    @Override
    public BookDTO getByName(String title) {
        Book book = bookRepository.findByTitle(title)
                .orElseThrow(() -> new BookException(String.format("Не удалось найти книгу с названием %s", title)));
        return modelMapper.map(book, BookDTO.class);
    }

    /**
     * Метод возвращает все книги
     *
     * @return список книг
     */
    @Override
    public List<BookDTO> getAll() {
        List<Book> all = bookRepository.findAll();
        if (all == null || all.isEmpty()) throw new BookException(String.format("Список книг пуст"));
        return all.stream()
                .map(book -> modelMapper.map(book, BookDTO.class))
                .collect(Collectors.toList());
    }
}
