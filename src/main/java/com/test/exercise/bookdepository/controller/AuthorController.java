package com.test.exercise.bookdepository.controller;

import com.test.exercise.bookdepository.dto.AuthorDTO;
import com.test.exercise.bookdepository.service.AuthorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/author")
public class AuthorController {
    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @PostMapping("add_author")
    public ResponseEntity addAuthorController(@RequestBody AuthorDTO authorDTO){
        authorService.addAuthor(authorDTO);
        return ResponseEntity.ok("Автор " + authorDTO.getName() + " добавлен");
    }
}
