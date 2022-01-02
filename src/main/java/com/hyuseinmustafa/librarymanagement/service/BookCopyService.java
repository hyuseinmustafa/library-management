package com.hyuseinmustafa.librarymanagement.service;

import com.hyuseinmustafa.librarymanagement.web.v1.model.GetBookCopyDto;
import com.hyuseinmustafa.librarymanagement.web.v1.model.PostBookCopyDto;
import org.springframework.data.util.Pair;

import java.util.List;

public interface BookCopyService {
    List<GetBookCopyDto> getAll();
    GetBookCopyDto getById(Long id);
    GetBookCopyDto create(PostBookCopyDto postBookCopyDto);
    Pair<GetBookCopyDto, ContentUpdateStatus> update(Long id, PostBookCopyDto postBookCopyDto);
}
