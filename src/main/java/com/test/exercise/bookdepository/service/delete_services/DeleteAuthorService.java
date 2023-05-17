package com.test.exercise.bookdepository.service.delete_services;

import com.test.exercise.bookdepository.exception.AuthorException;
import com.test.exercise.bookdepository.repository.AuthorRepository;
import com.test.exercise.bookdepository.service.add_services.AdderAuthorService;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class DeleteAuthorService implements DelEntity{

    private final static Logger LOGGER = LogManager.getLogger(DeleteAuthorService.class);

    private final AuthorRepository authorRepository;
    private final ModelMapper modelMapper;

    public DeleteAuthorService(AuthorRepository authorRepository,
                               ModelMapper modelMapper) {
        this.authorRepository = authorRepository;
        this.modelMapper = modelMapper;
    }

    /**
     * Удаление автора по полному совпадению
     *
     * @param sureName фамилия автора
     */
    @Override
    public void deleteByName(String sureName) {
        if (StringUtils.isNotBlank(sureName)) {
            LOGGER.error("Не удалось удалить автора из-за некорректного JSON");
            throw new AuthorException("Автор не был удалён");
        }
        try {
            authorRepository.deleteBySureName(sureName);
            LOGGER.info("Автор " + sureName + " был удалён");
        } catch (RuntimeException e) {
            LOGGER.error("Не удалось удалить автора по причине отсутствия его в БД");
            throw new AuthorException("Не удалось найти автора " + sureName);
        }
    }

    /**
     * Удаление автора по идентификатору
     *
     * @param authorId идентификатора автора
     */
    @Override
    public void deleteById(Long authorId) {
        if (authorId == null) {
            LOGGER.error("Не удалось удалить автора из-за некорректного JSON");
            throw new AuthorException("Автор не был удалён");
        }
        try {
            authorRepository.deleteById(authorId);
            LOGGER.info("Автор под индексом " + authorId + " был удалён");
        } catch (EmptyResultDataAccessException e) {
            LOGGER.error("Не удалось удалить автора по причине отсутствия его в БД");
            throw new AuthorException("Не удалось найти автора по " + authorId);
        }
    }
}