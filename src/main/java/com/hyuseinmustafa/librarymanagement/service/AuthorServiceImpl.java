package com.hyuseinmustafa.librarymanagement.service;

import com.hyuseinmustafa.librarymanagement.exception.NotFoundException;
import com.hyuseinmustafa.librarymanagement.domain.Author;
import com.hyuseinmustafa.librarymanagement.repository.AuthorRepository;
import com.hyuseinmustafa.librarymanagement.web.v1.mapper.AuthorMapper;
import com.hyuseinmustafa.librarymanagement.web.v1.model.GetAuthorDto;
import com.hyuseinmustafa.librarymanagement.web.v1.model.PostAuthorDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RequiredArgsConstructor
@Service
public class AuthorServiceImpl implements AuthorService{

    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    @Override
    public List<GetAuthorDto> getAll() {
        return StreamSupport.stream(authorRepository.findAll().spliterator(), false)
                .map(authorMapper::toGetAuthorDto).collect(Collectors.toList());
    }

    @Override
    public GetAuthorDto getById(Long id) {
        return authorMapper.toGetAuthorDto(authorRepository.findById(id)
                        .orElseThrow(() -> new NotFoundException()));
    }

    @Override
    public GetAuthorDto createNew(PostAuthorDto postAuthorDto) {
        return authorMapper.toGetAuthorDto(authorRepository.save(authorMapper.toAuthor(postAuthorDto)));
    }

    @Override
    public Pair<GetAuthorDto, ContentUpdateStatus> update(Long id, PostAuthorDto postAuthorDto) {
        Author author = authorRepository.findById(id).orElse(new Author());
        Author authorNew = authorMapper.toAuthor(postAuthorDto);
        author.setName(authorNew.getName());
        ContentUpdateStatus status = author.getId() == null ?
                ContentUpdateStatus.CREATED_NEW : ContentUpdateStatus.UPDATED;
        return Pair.of(authorMapper.toGetAuthorDto(authorRepository.save(author)), status);
    }
}
