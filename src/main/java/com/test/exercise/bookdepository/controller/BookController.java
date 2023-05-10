package com.test.exercise.bookdepository.controller;

import com.test.exercise.bookdepository.dto.BookDTO;
import com.test.exercise.bookdepository.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//TODO: Дописать доку по рестам
//TODO: Написать ExceptionHandle
//TODO: добавить логирование
@RestController
@RequestMapping("api/v1/book")
public class BookController {
    //TODO: Дописать ресты
    public final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("add")
    public ResponseEntity addBook(@RequestBody BookDTO bookDTO){
        bookService.addBook(bookDTO);
        return ResponseEntity.ok("Книга была добавлена");
    }
}
