package com.test.exercise.bookdepository.service.add_services;

import com.test.exercise.bookdepository.dto.GenreDTO;
import com.test.exercise.bookdepository.exception.GenreException;
import com.test.exercise.bookdepository.model.Genre;
import com.test.exercise.bookdepository.repository.GenreRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdderGenreService implements AddEntity<GenreDTO>{

    private final static Logger LOGGER = LogManager.getLogger(AdderGenreService.class);

    private final GenreRepository genreRepository;
    private final ModelMapper modelMapper;

    public AdderGenreService(GenreRepository genreRepository,
                             ModelMapper modelMapper) {
        this.genreRepository = genreRepository;
        this.modelMapper = modelMapper;
    }

    /**
     * Метод осуществляет добавление жанра в БД.
     *
     * @param genreDTO JSON объект жанра
     */
    @Override
    public void add(GenreDTO genreDTO) {
        if (genreDTO == null) {
            LOGGER.error("Не был добавлен жанр по причине некорректного JSON");
            throw new GenreException("Жанр не был передан");
        }
        Genre genre = modelMapper.map(genreDTO, Genre.class);
        try {
            genreRepository.saveAndFlush(genre);
            LOGGER.info(String.format("Жанр %s был добавлен", genre.getName()));
        } catch (RuntimeException e) {
            throw new GenreException("Не удалось добавить Жанр");
        }
    }

    @Override
    public void addAll(List<GenreDTO> listGenresDTO) {
        if (listGenresDTO == null || listGenresDTO.isEmpty()) {
            LOGGER.error("Не были добавлены жанры по причине некорректного JSON");
            throw new GenreException("Жанры не был передан");
        }
        List<Genre> genres = listGenresDTO.stream()
                .map(genreDTO -> modelMapper.map(genreDTO, Genre.class))
                .collect(Collectors.toList());
        genreRepository.saveAllAndFlush(genres);
        LOGGER.info("Список из жанров был добавлен");
    }
}
