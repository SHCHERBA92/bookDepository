package com.test.exercise.bookdepository.service;

import com.test.exercise.bookdepository.dto.BookDTO;
import com.test.exercise.bookdepository.exception.BookException;
import com.test.exercise.bookdepository.model.Book;
import com.test.exercise.bookdepository.repository.BookRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Set;

//TODO: Дописать доку по методам
@Service
public class BookService {
    public final BookRepository bookRepository;
    public final ModelMapper modelMapper;

    public BookService(BookRepository bookRepository,
                       ModelMapper modelMapper) {
        this.bookRepository = bookRepository;
        this.modelMapper = modelMapper;
    }

    public void addBook(BookDTO bookDTO){
        if (bookDTO == null) throw new BookException("Книга не задана");
        Book book = modelMapper.map(bookDTO, Book.class);
        bookRepository.saveAndFlush(book);
    }

    //TODO: Дописать остальные CRUD методы
    public void addBooks(Set<BookDTO> bookDTOList){

    }
}
