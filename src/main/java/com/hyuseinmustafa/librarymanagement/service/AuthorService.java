package com.hyuseinmustafa.librarymanagement.service;

import com.hyuseinmustafa.librarymanagement.web.v1.model.AuthorDto;

import java.util.List;

public interface AuthorService {
    List<AuthorDto> getAll();
    AuthorDto getById(Long id);
    AuthorDto newAuthor(AuthorDto authorDto);
    AuthorDto updateAuthor(Long id, AuthorDto authorDto);
}
