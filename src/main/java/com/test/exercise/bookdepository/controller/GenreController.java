package com.test.exercise.bookdepository.controller;

import com.test.exercise.bookdepository.dto.GenreDTO;
import com.test.exercise.bookdepository.service.GenreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//TODO: Написать ExceptionHandle

/**
 * Контроллер для работы с REST запросами жанра
 */
@RestController
@RequestMapping("api/v1/genre")
//TODO: добавить логирование
public class GenreController {

    private final GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    /**
     * Получение жанра по его id. Полный путь запроса - http://localhost:8080/api/v1/genre/{id}
     * 
     * @param id идентификатор жанра
     * @return жанр по найденному id или сообщение об ошибки
     */
    @GetMapping("{id}")
    public ResponseEntity getGenreById(@PathVariable Long id){
        GenreDTO genre = genreService.getGenre(id);
        return ResponseEntity.ok(genre);
    }

    /**
     * Получение жанра по его названию. Полный путь запроса - http://localhost:8080/api/v1/genre?name=
     *
     * @param name параметр запроса
     * @return жанр по найденному имени или сообщение об ошибки
     */
    @GetMapping()
    public ResponseEntity getGenreById(@RequestParam(name = "name") String name){
        GenreDTO genre = genreService.getGenre(name);
        return ResponseEntity.ok(genre);
    }

    /**
     * Получение всех жанров
     * @return список жанров или сообщение об ошибки
     */
    @GetMapping("all")
    public ResponseEntity getAllGenre(){
        List<GenreDTO> allGenre = genreService.getAllGenre();
        return ResponseEntity.ok(allGenre);
    }

    /**
     * Добавление нового жанра
     *
     * @param genreDTO жанр
     * @return сообщение о том, что жанр добавлен или сообщение об ошибки
     */
    @PostMapping("add")
    public ResponseEntity addGenre(@RequestBody GenreDTO genreDTO){
        genreService.addGenre(genreDTO);
        return ResponseEntity.ok("Жанр " + genreDTO.getName() + " добавлен");
    }
}
