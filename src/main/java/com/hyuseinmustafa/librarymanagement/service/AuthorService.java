package com.hyuseinmustafa.librarymanagement.service;

import com.hyuseinmustafa.librarymanagement.web.v1.model.AuthorDto;
import org.springframework.data.util.Pair;

import java.util.List;

public interface AuthorService {
    List<AuthorDto> getAll();
    AuthorDto getById(Long id);
    AuthorDto createNew(AuthorDto authorDto);
    Pair<AuthorDto, ContentUpdateStatus> update(Long id, AuthorDto authorDto);
}
