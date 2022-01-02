package com.hyuseinmustafa.librarymanagement.web.v1.mapper;

import com.hyuseinmustafa.librarymanagement.domain.Book;
import com.hyuseinmustafa.librarymanagement.web.v1.model.GetBookDto;
import com.hyuseinmustafa.librarymanagement.web.v1.model.PostBookDto;
import org.mapstruct.Mapper;

@Mapper
public interface BookMapper {

    GetBookDto toGetBookDto(Book book);

    Book toBook(PostBookDto postBookDto);
}
