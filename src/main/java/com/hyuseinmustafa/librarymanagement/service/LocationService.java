package com.hyuseinmustafa.librarymanagement.service;

import com.hyuseinmustafa.librarymanagement.web.v1.model.GetLocationDto;
import com.hyuseinmustafa.librarymanagement.web.v1.model.PostLocationDto;
import org.springframework.data.util.Pair;

import java.util.List;

public interface LocationService {
    List<GetLocationDto> getAll();
    GetLocationDto getById(Long id);
    GetLocationDto create(PostLocationDto postLocationDto);
    Pair<GetLocationDto, ContentUpdateStatus> update(Long id, PostLocationDto postLocationDto);
}
