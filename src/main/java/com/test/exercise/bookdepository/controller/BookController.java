package com.test.exercise.bookdepository.controller;

import com.test.exercise.bookdepository.dto.BookDTO;
import com.test.exercise.bookdepository.model.Book;
import com.test.exercise.bookdepository.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

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

    /**
     * Post endpoint по добавлению книги.
     *
     * @param bookDTO JSON книги
     * @return Ответ со статусом 200 и сообщение об успехе или сообщение об ошибки.
     */
    @PostMapping("add")
    public ResponseEntity addBook(@RequestBody BookDTO bookDTO){
        bookService.addBook(bookDTO);
        return ResponseEntity.ok("Книга была добавлена");
    }

    /**
     * Get endpoint для извлечения книги по её идентификатору, который передан как часть URI.
     *
     * @param id часть URI запроса
     * @return Ответ со статусом 200 и телом содержащее JSON сущность книги или текстовое сообщение об ошибки.
     */
    @GetMapping("{id}")
    public ResponseEntity getBook(@PathVariable Long id){
        BookDTO book = bookService.getBook(id);
        return ResponseEntity.ok(book);
    }

    /**
     * Get endpoint для извлечения всех книг
     *
     * @return Ответ со статусом 200 и телом содержащее список книг.
     */
    @GetMapping()
    public ResponseEntity getAllBooks(){
        List<BookDTO> allBook = bookService.getAllBook();
        return ResponseEntity.ok(allBook);
    }


}
