package com.hyuseinmustafa.librarymanagement.web.v1.mapper;

import com.hyuseinmustafa.librarymanagement.domain.BookCopy;
import com.hyuseinmustafa.librarymanagement.web.v1.model.GetBookCopyDto;
import org.mapstruct.Mapper;

@Mapper
public interface BookCopyMapper {

    GetBookCopyDto toGetBookCopyDto(BookCopy bookCopy);
}
