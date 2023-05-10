package com.test.exercise.bookdepository.controller;

import com.test.exercise.bookdepository.dto.AuthorDTO;
import com.test.exercise.bookdepository.service.AuthorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//TODO: Дописать доку по рестам
//TODO: Написать ExceptionHandle
//TODO: добавить логирование
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

    @GetMapping("all")
    public List<AuthorDTO> getAllAuthor(){
        return authorService.getAllAuthors();
    }

    @GetMapping("{id}")
    public AuthorDTO getAuthorById(@PathVariable Long id){
        return authorService.getAuthor(id);
    }

    @GetMapping()
    public AuthorDTO getAuthorById(@RequestParam(name = "name") String name,
                                   @RequestParam(name = "sure_name") String sureName){
        return authorService.getAuthor(name, sureName);
    }

}
