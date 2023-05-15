package com.test.exercise.bookdepository.service.add_services;

import com.test.exercise.bookdepository.dto.AuthorDTO;
import com.test.exercise.bookdepository.exception.AuthorException;
import com.test.exercise.bookdepository.model.Author;
import com.test.exercise.bookdepository.repository.AuthorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdderAuthorService implements AddEntity<AuthorDTO>{
    private final AuthorRepository authorRepository;
    private final ModelMapper modelMapper;

    public AdderAuthorService(AuthorRepository authorRepository,
                              ModelMapper modelMapper) {
        this.authorRepository = authorRepository;
        this.modelMapper = modelMapper;
    }

    /**
     * Добавление автора в БД
     *
     * @param authorDTO JSON автора
     */
    @Override
    public void add(AuthorDTO authorDTO) {
        if (authorDTO == null) throw new AuthorException("Автор не был добавлен");
        Author author = modelMapper.map(authorDTO, Author.class);
        authorRepository.saveAndFlush(author);
    }

    /**
     * Добавление список авторов в БД
     *
     * @param authorsDTO JSON автора
     */
    @Override
    public void addAll(List<AuthorDTO> authorsDTO) {
        if (authorsDTO.isEmpty() || authorsDTO == null) throw new AuthorException("Автор не был добавлен");
        List<Author> authors = authorsDTO.stream()
                .map(authorDTO -> modelMapper.map(authorDTO, Author.class))
                .collect(Collectors.toList());
        authorRepository.saveAllAndFlush(authors);
    }
}
