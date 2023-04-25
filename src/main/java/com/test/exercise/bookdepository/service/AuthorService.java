package com.test.exercise.bookdepository.service;

import com.test.exercise.bookdepository.dto.AuthorDTO;
import com.test.exercise.bookdepository.model.Author;
import com.test.exercise.bookdepository.repository.AuthorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

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
        authorRepository.save(author);
    }
}
