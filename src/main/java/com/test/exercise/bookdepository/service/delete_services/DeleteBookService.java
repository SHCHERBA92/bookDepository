package com.test.exercise.bookdepository.service.delete_services;

import com.test.exercise.bookdepository.exception.BookException;
import com.test.exercise.bookdepository.repository.BookRepository;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class DeleteBookService implements DelEntity{

    private final BookRepository bookRepository;

    public DeleteBookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    /**
     * Удаление книги по её названию
     *
     * @param title название книги
     */
    @Override
    public void deleteByName(String title) {
        if (StringUtils.isBlank(title)) throw new BookException("Не введено название книги");
        try {
            bookRepository.deleteByTitle(title);
        }catch (RuntimeException e){
            throw new BookException(String.format("Не удалось удалить книгу под названием %s", title));
        }
    }

    /**
     * Удаление книги по её идентификатору
     *
     * @param id идентификатор книги
     */
    @Override
    public void deleteById(Long id) {
        if (id==null) throw new BookException("Не задан идентификатор книги");
        try {
            bookRepository.deleteById(id);
        }catch (RuntimeException e){
            throw new BookException("Не удалось удалить книгу");
        }
    }
}
