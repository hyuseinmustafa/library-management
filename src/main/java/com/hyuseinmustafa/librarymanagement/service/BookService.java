package com.hyuseinmustafa.librarymanagement.service;

import com.hyuseinmustafa.librarymanagement.web.v1.model.BookDto;
import org.springframework.data.util.Pair;

import java.util.List;

public interface BookService {
    List<BookDto> getAll();
    BookDto getById(Long id);
    BookDto createNew(BookDto bookDto);
    Pair<BookDto, ContentUpdateStatus> update(Long id, BookDto bookDto);
}
