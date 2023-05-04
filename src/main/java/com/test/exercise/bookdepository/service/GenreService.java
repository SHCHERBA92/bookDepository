package com.test.exercise.bookdepository.service;

import com.test.exercise.bookdepository.dto.GenreDTO;
import com.test.exercise.bookdepository.exception.GenreException;
import com.test.exercise.bookdepository.model.Genre;
import com.test.exercise.bookdepository.repository.GenreRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GenreService {

    private final GenreRepository genreRepository;
    private final ModelMapper modelMapper;

    public GenreService(GenreRepository genreRepository,
                        ModelMapper modelMapper) {
        this.genreRepository = genreRepository;
        this.modelMapper = modelMapper;
    }

    public void addGenre(GenreDTO genreDTO){
        if (genreDTO == null) throw new GenreException("Жанр не был передан");
        Genre genre = modelMapper.map(genreDTO, Genre.class);
        try {
            genreRepository.saveAndFlush(genre);
        }catch (RuntimeException e){
            throw new GenreException("Не удалось добавить Жанр");
        }
    }

    public List<GenreDTO> getAllGenre(){
        List<GenreDTO> all = genreRepository.findAll().stream()
                .map(genre -> modelMapper.map(genre, GenreDTO.class))
                .collect(Collectors.toList());
        if (all==null || all.isEmpty()) throw new GenreException("Список жанров пуст");
        return all;
    }

    public GenreDTO getGenre(Long genreId){
        if (genreId==null) throw new GenreException("Не указа id для поиска жанра");
        Genre genre = genreRepository.findById(genreId).orElseThrow(() -> new GenreException("не удалось жанр по  id " + genreId));
        return modelMapper.map(genre, GenreDTO.class);
    }

    public GenreDTO getGenre(String name){
        Genre genre = genreRepository.findByName(name).orElseThrow(() -> new GenreException("не удалось жанр по названию " + name));
        return modelMapper.map(genre, GenreDTO.class);
    }
}
