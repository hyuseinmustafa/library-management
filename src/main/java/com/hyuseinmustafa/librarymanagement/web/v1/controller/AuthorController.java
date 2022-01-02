package com.hyuseinmustafa.librarymanagement.web.v1.controller;

import com.hyuseinmustafa.librarymanagement.service.AuthorService;
import com.hyuseinmustafa.librarymanagement.Exception.NotFoundException;
import com.hyuseinmustafa.librarymanagement.web.v1.model.AuthorDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(("/api/v1/authors"))
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping
    public ResponseEntity<List<AuthorDto>> getAllAuthors(){
        return new ResponseEntity(authorService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorDto> getAuthor(@PathVariable Long id){
        return new ResponseEntity(authorService.getById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<AuthorDto> newAuthor(@RequestBody AuthorDto authorDto){
        return new ResponseEntity(authorService.newAuthor(authorDto), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AuthorDto> updateAuthor(@PathVariable Long id,
                                                  @RequestBody AuthorDto authorDto){
        return new ResponseEntity(authorService.updateAuthor(id, authorDto), HttpStatus.OK);
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String NotFoundException(NotFoundException ex){
        return ex.getMessage();
    }
}
