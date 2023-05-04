package com.test.exercise.bookdepository.controller;

import com.test.exercise.bookdepository.dto.GenreDTO;
import com.test.exercise.bookdepository.service.GenreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/genre")
public class GenreController {

    private final GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping("{id}")
    public ResponseEntity getGenreById(@PathVariable Long id){
        GenreDTO genre = genreService.getGenre(id);
        return ResponseEntity.ok(genre);
    }

    @GetMapping()
    public ResponseEntity getGenreById(@RequestParam(name = "name") String name){
        GenreDTO genre = genreService.getGenre(name);
        return ResponseEntity.ok(genre);
    }

    @GetMapping("all")
    public ResponseEntity getAllGenre(){
        List<GenreDTO> allGenre = genreService.getAllGenre();
        return ResponseEntity.ok(allGenre);
    }

    @PostMapping("add")
    public ResponseEntity addGenre(@RequestBody GenreDTO genreDTO){
        genreService.addGenre(genreDTO);
        return ResponseEntity.ok("Жанр " + genreDTO.getName() + " добавлен");
    }
}
