package com.hyuseinmustafa.librarymanagement.web.v1.controller;

import com.hyuseinmustafa.librarymanagement.service.BookCopyService;
import com.hyuseinmustafa.librarymanagement.service.ContentUpdateStatus;
import com.hyuseinmustafa.librarymanagement.web.v1.model.GetBookCopyDto;
import com.hyuseinmustafa.librarymanagement.web.v1.model.PostBookCopyDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/bookcopies")
public class BookCopyController {

    private final BookCopyService bookCopyService;

    @GetMapping
    public ResponseEntity<List<GetBookCopyDto>> getAllBookCopies(){
        return new ResponseEntity<>(bookCopyService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetBookCopyDto> getBookCopy(@PathVariable Long id){
        return new ResponseEntity<>(bookCopyService.getById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<GetBookCopyDto> createBookCopy(@RequestBody @Valid PostBookCopyDto postBookCopyDto){
        return new ResponseEntity<>(bookCopyService.create(postBookCopyDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GetBookCopyDto> updateBookCopy(@PathVariable Long id,
                                                         @RequestBody @Valid PostBookCopyDto postBookCopyDto){
        Pair pair = bookCopyService.update(id, postBookCopyDto);
        return new ResponseEntity(pair.getFirst(),
                pair.getSecond() == ContentUpdateStatus.UPDATED ? HttpStatus.OK : HttpStatus.CREATED);
    }
}
