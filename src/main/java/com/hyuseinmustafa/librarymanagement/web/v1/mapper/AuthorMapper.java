package com.hyuseinmustafa.librarymanagement.web.v1.mapper;

import com.hyuseinmustafa.librarymanagement.domain.Author;
import com.hyuseinmustafa.librarymanagement.web.v1.model.AuthorDto;
import org.mapstruct.Mapper;

@Mapper
public interface AuthorMapper {

    AuthorDto toAuthorDto(Author author);

    Author toAuthor(AuthorDto authorDto);
}
