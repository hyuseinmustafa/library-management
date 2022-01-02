package com.hyuseinmustafa.librarymanagement.web.v1.controller;

import com.hyuseinmustafa.librarymanagement.service.BookService;
import com.hyuseinmustafa.librarymanagement.service.ContentUpdateStatus;
import com.hyuseinmustafa.librarymanagement.web.v1.model.GetBookDto;
import com.hyuseinmustafa.librarymanagement.web.v1.model.PostBookDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/books")
public class BookController {

    private final BookService bookService;

    @GetMapping
    public ResponseEntity<List<GetBookDto>> getAllBooks(){
        return new ResponseEntity(bookService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetBookDto> getBook(@PathVariable Long id){
        return new ResponseEntity(bookService.getById(id), HttpStatus.OK);
    }

    @PostMapping
    public  ResponseEntity<GetBookDto> saveNewBook(@RequestBody @Valid PostBookDto postBookDto){
        return new ResponseEntity(bookService.createNew(postBookDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GetBookDto> updateBook(@PathVariable Long id,
                                                 @RequestBody @Valid PostBookDto postBookDto){
        Pair pair = bookService.update(id, postBookDto);
        return new ResponseEntity(pair.getFirst(),
                (pair.getSecond() == ContentUpdateStatus.UPDATED) ? HttpStatus.OK : HttpStatus.CREATED);
    }
}
