package com.hyuseinmustafa.librarymanagement.service;

import com.hyuseinmustafa.librarymanagement.web.v1.model.GetBookDto;
import com.hyuseinmustafa.librarymanagement.web.v1.model.PostBookDto;
import org.springframework.data.util.Pair;

import java.util.List;

public interface BookService {
    List<GetBookDto> getAll();
    GetBookDto getById(Long id);
    GetBookDto create(PostBookDto postBookDto);
    Pair<GetBookDto, ContentUpdateStatus> update(Long id, PostBookDto postBookDto);
}
