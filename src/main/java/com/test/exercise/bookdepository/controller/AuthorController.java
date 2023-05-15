package com.test.exercise.bookdepository.controller;

import com.test.exercise.bookdepository.dto.AuthorDTO;
import com.test.exercise.bookdepository.service.add_services.AdderAuthorService;
import com.test.exercise.bookdepository.service.get_services.GetterAuthorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//TODO: Дописать доку по рестам
//TODO: Написать ExceptionHandle
//TODO: добавить логирование
@RestController
@RequestMapping("api/v1/author")
public class AuthorController {

    private final GetterAuthorService getterAuthorService;
    private final AdderAuthorService adderAuthorService;

    public AuthorController(GetterAuthorService getterAuthorService,
                            AdderAuthorService adderAuthorService) {
        this.getterAuthorService = getterAuthorService;
        this.adderAuthorService = adderAuthorService;
    }

    @PostMapping("add_author")
    public ResponseEntity addAuthorController(@RequestBody AuthorDTO authorDTO){
        adderAuthorService.add(authorDTO);
        return ResponseEntity.ok("Автор " + authorDTO.getName() + " добавлен");
    }

    @GetMapping("all")
    public List<AuthorDTO> getAllAuthor(){
        return getterAuthorService.getAll();
    }

    @GetMapping("{id}")
    public AuthorDTO getAuthorById(@PathVariable Long id){
        return getterAuthorService.getById(id);
    }

    @GetMapping
    public AuthorDTO getAuthorById(@RequestParam(name = "name") String name,
                                   @RequestParam(name = "sure_name") String sureName){
        return getterAuthorService.getByName(name, sureName);
    }

}
