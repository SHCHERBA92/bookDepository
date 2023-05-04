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

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;
    private final ModelMapper modelMapper;

    public AuthorService(AuthorRepository authorRepository,
                         ModelMapper modelMapper) {
        this.authorRepository = authorRepository;
        this.modelMapper = modelMapper;
    }

    public void addAuthor(AuthorDTO authorDTO){
        if (authorDTO == null) throw new AuthorException("Автор не был добавлен");
        Author author = modelMapper.map(authorDTO, Author.class);
        authorRepository.saveAndFlush(author);
    }

    public void deleteAuthor(AuthorDTO authorDTO){
        if (authorDTO == null) throw new AuthorException("Автор не был удалён");
        Author author = modelMapper.map(authorDTO, Author.class);
        try {
            authorRepository.delete(author);
        }catch (RuntimeException e){
            throw new AuthorException("Не удалось найти автора " + author.getName() + " " + author.getSureName());
        }

    }

    public void deleteAuthor(Long authorId){
        if (authorId == null) throw new AuthorException("Автор не был удалён");
        try {
            authorRepository.deleteById(authorId);
        }catch (EmptyResultDataAccessException e){
            throw new AuthorException("Не удалось найти автора по " + authorId);
        }
    }

    public List<AuthorDTO> getAllAuthors(){
        List<AuthorDTO> authorDTOList = null;
        try {
            authorDTOList = authorRepository.findAll().stream()
                    .map(author -> modelMapper.map(author, AuthorDTO.class))
                    .collect(Collectors.toList());
        }catch (RuntimeException e){
            throw new AuthorException("Список авторов пуст");
        }
        if (authorDTOList == null || authorDTOList.isEmpty()){
            throw new AuthorException("Список авторов пуст");
        }
        return authorDTOList;
    }

    public AuthorDTO getAuthor(Long authorId){
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> {
                    throw new AuthorException("Автор под id " + authorId + " не найден");
                });
        return modelMapper.map(author, AuthorDTO.class);
    }

    public AuthorDTO getAuthor(String authorName, String authorSureName){
        Author author = authorRepository.findAuthorByNameAndSureName(authorName, authorSureName)
                .orElseThrow(() -> {
                    throw new AuthorException(String.format("Автор по имени %s %s не найден", authorName, authorSureName));
                });
        return modelMapper.map(author, AuthorDTO.class);
    }
}
