package com.hyuseinmustafa.librarymanagement.service;

import com.hyuseinmustafa.librarymanagement.web.v1.model.GetAuthorDto;
import com.hyuseinmustafa.librarymanagement.web.v1.model.PostAuthorDto;
import org.springframework.data.util.Pair;

import java.util.List;

public interface AuthorService {
    List<GetAuthorDto> getAll();
    GetAuthorDto getById(Long id);
    GetAuthorDto create(PostAuthorDto authorDto);
    Pair<GetAuthorDto, ContentUpdateStatus> update(Long id, PostAuthorDto authorDto);
}
