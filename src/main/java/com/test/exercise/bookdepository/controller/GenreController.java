package com.test.exercise.bookdepository.controller;

import com.test.exercise.bookdepository.dto.GenreDTO;
import com.test.exercise.bookdepository.service.add_services.AdderGenreService;
import com.test.exercise.bookdepository.service.get_services.GetterGenreService;
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

    private final GetterGenreService getterGenreService;
    private final AdderGenreService adderGenreService;

    public GenreController(GetterGenreService getterGenreService,
                           AdderGenreService adderGenreService) {
        this.getterGenreService = getterGenreService;
        this.adderGenreService = adderGenreService;
    }

    /**
     * Получение жанра по его id. Полный путь запроса - http://localhost:8080/api/v1/genre/{id}
     * 
     * @param id идентификатор жанра
     * @return жанр по найденному id или сообщение об ошибки
     */
    @GetMapping("{id}")
    public ResponseEntity getGenreById(@PathVariable Long id){
        GenreDTO genre = getterGenreService.getById(id);
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
        GenreDTO genre = getterGenreService.getByName(name);
        return ResponseEntity.ok(genre);
    }

    /**
     * Получение всех жанров
     * @return список жанров или сообщение об ошибки
     */
    @GetMapping("all")
    public ResponseEntity getAllGenre(){
        List<GenreDTO> allGenre = getterGenreService.getAll();
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
        adderGenreService.add(genreDTO);
        return ResponseEntity.ok("Жанр " + genreDTO.getName() + " добавлен");
    }
}
