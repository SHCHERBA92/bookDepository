package com.test.exercise.bookdepository.service.delete_services;

import com.test.exercise.bookdepository.exception.BookException;
import com.test.exercise.bookdepository.repository.BookRepository;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
public class DeleteBookService implements DelEntity{

    private final static Logger LOGGER = LogManager.getLogger(DeleteBookService.class);

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
        if (StringUtils.isBlank(title)) {
            LOGGER.error("Не удалось удалить книгу из-за некорректного JSON");
            throw new BookException("Не введено название книги");
        }
        try {
            bookRepository.deleteByTitle(title);
        }catch (RuntimeException e){
            LOGGER.error(String.format("Не удалось удалить книгу под названием %s", title));
            throw new BookException(String.format("Не удалось удалить книгу под названием %s", title));
        }
        LOGGER.info("Книга " + title + " была удалённа");
    }

    /**
     * Удаление книги по её идентификатору
     *
     * @param id идентификатор книги
     */
    @Override
    public void deleteById(Long id) {
        if (id==null) {
            LOGGER.error("Не удалось удалить книгу из-за некорректного JSON");
            throw new BookException("Не задан идентификатор книги");
        }
        try {
            bookRepository.deleteById(id);
        }catch (RuntimeException e){
            LOGGER.error(String.format("Не удалось удалить книгу под id %l", id));
            throw new BookException("Не удалось удалить книгу");
        }
        LOGGER.info("Книга под id = " + id + " была удалённа");
    }
}
