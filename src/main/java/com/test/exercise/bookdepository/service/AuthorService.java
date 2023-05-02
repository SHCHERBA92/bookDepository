package com.test.exercise.bookdepository.service;

import com.test.exercise.bookdepository.dto.AuthorDTO;
import com.test.exercise.bookdepository.exception.AuthorException;
import com.test.exercise.bookdepository.model.Author;
import com.test.exercise.bookdepository.repository.AuthorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;
    private final ModelMapper modelMapper;

    public AuthorService(AuthorRepository authorRepository, ModelMapper modelMapper) {
        this.authorRepository = authorRepository;
        this.modelMapper = modelMapper;
    }

    public void addAuthor(AuthorDTO authorDTO){
        Author author = modelMapper.map(authorDTO, Author.class);
        authorRepository.saveAndFlush(author);
    }

    public void deleteAuthor(AuthorDTO authorDTO){
        Author author = modelMapper.map(authorDTO, Author.class);
        authorRepository.delete(author);
    }

    public void deleteAuthor(Long authorId){
        authorRepository.deleteById(authorId);
    }

    public List<AuthorDTO> getAllAuthors(){
        return authorRepository.findAll().stream()
                .map(author -> modelMapper.map(author, AuthorDTO.class))
                .collect(Collectors.toList());
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
