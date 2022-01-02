package com.hyuseinmustafa.librarymanagement.web.v1.controller;

import com.hyuseinmustafa.librarymanagement.Exception.NotFoundException;
import com.hyuseinmustafa.librarymanagement.service.BookService;
import com.hyuseinmustafa.librarymanagement.service.ContentUpdateStatus;
import com.hyuseinmustafa.librarymanagement.web.v1.model.BookDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/books")
public class BookController {

    private final BookService bookService;

    @GetMapping
    public ResponseEntity<List<BookDto>> getAllBooks(){
        return new ResponseEntity(bookService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDto> getBook(@PathVariable Long id){
        return new ResponseEntity(bookService.getById(id), HttpStatus.OK);
    }

    @PostMapping
    public  ResponseEntity<BookDto> saveNewBook(@RequestBody BookDto bookDto){
        return new ResponseEntity(bookService.createNew(bookDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookDto> updateBook(@PathVariable Long id,
                                              @RequestBody BookDto bookDto){
        Pair pair = bookService.update(id, bookDto);
        return new ResponseEntity(pair.getFirst(),
                (pair.getSecond() == ContentUpdateStatus.UPDATED) ? HttpStatus.OK : HttpStatus.CREATED);
    }
}
