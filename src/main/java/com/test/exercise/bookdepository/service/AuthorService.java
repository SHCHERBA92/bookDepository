package com.test.exercise.bookdepository.service;

import com.test.exercise.bookdepository.dto.AuthorDTO;
import com.test.exercise.bookdepository.exception.AuthorException;
import com.test.exercise.bookdepository.model.Author;
import com.test.exercise.bookdepository.repository.AuthorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

//TODO: добавить логирование
//TODO: перенести оставшиеся методы
@Service
public class AuthorService {
    private final AuthorRepository authorRepository;
    private final ModelMapper modelMapper;

    public AuthorService(AuthorRepository authorRepository,
                         ModelMapper modelMapper) {
        this.authorRepository = authorRepository;
        this.modelMapper = modelMapper;
    }

    /**
     * Удаление автора по полному совпадению
     *
     * @param authorDTO JSON автора
     */
    public void deleteAuthor(AuthorDTO authorDTO) {
        if (authorDTO == null) throw new AuthorException("Автор не был удалён");
        Author author = modelMapper.map(authorDTO, Author.class);
        try {
            authorRepository.delete(author);
        } catch (RuntimeException e) {
            throw new AuthorException("Не удалось найти автора " + author.getName() + " " + author.getSureName());
        }
    }

    /**
     * Удаление автора по идентификатору
     *
     * @param authorId идентификатора автора
     */
    public void deleteAuthor(Long authorId) {
        if (authorId == null) throw new AuthorException("Автор не был удалён");
        try {
            authorRepository.deleteById(authorId);
        } catch (EmptyResultDataAccessException e) {
            throw new AuthorException("Не удалось найти автора по " + authorId);
        }
    }
}
