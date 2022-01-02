package com.hyuseinmustafa.librarymanagement.web.v1.controller;

import com.hyuseinmustafa.librarymanagement.service.AuthorService;
import com.hyuseinmustafa.librarymanagement.service.ContentUpdateStatus;
import com.hyuseinmustafa.librarymanagement.web.v1.model.GetAuthorDto;
import com.hyuseinmustafa.librarymanagement.web.v1.model.PostAuthorDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(("/api/v1/authors"))
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping
    public ResponseEntity<List<GetAuthorDto>> getAllAuthors(){
        return new ResponseEntity(authorService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetAuthorDto> getAuthor(@PathVariable Long id){
        return new ResponseEntity(authorService.getById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<GetAuthorDto> newAuthor(@RequestBody @Valid PostAuthorDto authorDto){
        return new ResponseEntity(authorService.createNew(authorDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GetAuthorDto> updateAuthor(@PathVariable Long id,
                                                     @RequestBody @Valid PostAuthorDto authorDto){
        Pair pair = authorService.update(id, authorDto);
        return new ResponseEntity(pair.getFirst(),
                pair.getSecond() == ContentUpdateStatus.UPDATED ? HttpStatus.OK : HttpStatus.CREATED);
    }
}
