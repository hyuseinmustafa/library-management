package com.hyuseinmustafa.librarymanagement.web.v1.mapper;

import com.hyuseinmustafa.librarymanagement.domain.Book;
import com.hyuseinmustafa.librarymanagement.web.v1.model.BookDto;
import org.mapstruct.Mapper;

@Mapper
public interface BookMapper {

    BookDto toBookDto(Book book);

    Book toBook(BookDto bookDto);
}
