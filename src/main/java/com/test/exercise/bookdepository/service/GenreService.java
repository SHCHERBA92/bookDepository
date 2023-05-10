package com.test.exercise.bookdepository.service;

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

//TODO: добавить логирование
@Service
public class GenreService {

    private final static Logger LOGGER = LogManager.getLogger(GenreService.class);
    private final GenreRepository genreRepository;
    private final ModelMapper modelMapper;

    public GenreService(GenreRepository genreRepository,
                        ModelMapper modelMapper) {
        this.genreRepository = genreRepository;
        this.modelMapper = modelMapper;
    }

    /**
     * Метод осуществляет добавление жанра в БД.
     *
     * @param genreDTO JSON объект жанра
     */
    public void addGenre(GenreDTO genreDTO) {
        if (genreDTO == null) throw new GenreException("Жанр не был передан");
        Genre genre = modelMapper.map(genreDTO, Genre.class);
        try {
            genreRepository.saveAndFlush(genre);
            LOGGER.info(String.format("Жанр %s был добавлен", genre.getName()));
        } catch (RuntimeException e) {
            throw new GenreException("Не удалось добавить Жанр");
        }
    }

    /**
     * Метод который позволяет извлечь из БД все жанры
     *
     * @return список жанров или выводит ошибку
     */
    public List<GenreDTO> getAllGenre() {
        List<GenreDTO> all = genreRepository.findAll().stream()
                .map(genre -> modelMapper.map(genre, GenreDTO.class))
                .collect(Collectors.toList());
        if (all == null || all.isEmpty()) throw new GenreException("Список жанров пуст");
        LOGGER.info("Выдан список жанров");
        return all;
    }

    /**
     * Метод в котором осуществлён поиск среди жанров по идентификатору
     *
     * @param genreId идентификатор жанра
     * @return жанр по идентификатору или ошибку
     */
    public GenreDTO getGenre(Long genreId) {
        if (genreId == null) throw new GenreException("Не указа id для поиска жанра");
        Genre genre = genreRepository.findById(genreId).orElseThrow(() -> new GenreException("не удалось жанр по  id " + genreId));
        return modelMapper.map(genre, GenreDTO.class);
    }

    /**
     * Метод в котором осуществлён поиск среди жанров по названию
     *
     * @param name название жанра
     * @return жанр по названию или ошибку
     */
    public GenreDTO getGenre(String name) {
        Genre genre = genreRepository.findByName(name).orElseThrow(() -> new GenreException("не удалось жанр по названию " + name));
        return modelMapper.map(genre, GenreDTO.class);
    }
}
