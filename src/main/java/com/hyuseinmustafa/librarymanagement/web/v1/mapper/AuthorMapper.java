package com.hyuseinmustafa.librarymanagement.web.v1.mapper;

import com.hyuseinmustafa.librarymanagement.domain.Author;
import com.hyuseinmustafa.librarymanagement.web.v1.model.GetAuthorDto;
import com.hyuseinmustafa.librarymanagement.web.v1.model.PostAuthorDto;
import org.mapstruct.Mapper;

@Mapper
public interface AuthorMapper {

    GetAuthorDto toGetAuthorDto(Author author);

    Author toAuthor(PostAuthorDto authorDto);
}
