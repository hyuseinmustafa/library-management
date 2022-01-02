package com.hyuseinmustafa.librarymanagement.service;

import com.hyuseinmustafa.librarymanagement.web.v1.model.AuthorDto;
import org.springframework.data.util.Pair;

import java.util.List;

public interface AuthorService {
    List<AuthorDto> getAll();
    AuthorDto getById(Long id);
    AuthorDto newAuthor(AuthorDto authorDto);
    Pair<AuthorDto, ContentUpdateStatus> updateAuthor(Long id, AuthorDto authorDto);
}
