package com.hyuseinmustafa.librarymanagement.service;

import com.hyuseinmustafa.librarymanagement.exception.NotFoundException;
import com.hyuseinmustafa.librarymanagement.domain.Author;
import com.hyuseinmustafa.librarymanagement.repository.AuthorRepository;
import com.hyuseinmustafa.librarymanagement.web.v1.mapper.AuthorMapper;
import com.hyuseinmustafa.librarymanagement.web.v1.model.AuthorDto;
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
    public List<AuthorDto> getAll() {
        return StreamSupport.stream(authorRepository.findAll().spliterator(), false)
                .map(authorMapper::toAuthorDto).collect(Collectors.toList());
    }

    @Override
    public AuthorDto getById(Long id) {
        return authorMapper.toAuthorDto(authorRepository.findById(id)
                        .orElseThrow(() -> new NotFoundException()));
    }

    @Override
    public AuthorDto newAuthor(AuthorDto authorDto) {
        return authorMapper.toAuthorDto(authorRepository.save(authorMapper.toAuthor(authorDto)));
    }

    @Override
    public Pair<AuthorDto, ContentUpdateStatus> updateAuthor(Long id, AuthorDto authorDto) {
        Author author = authorRepository.findById(id).orElse(new Author());
        Author authorNew = authorMapper.toAuthor(authorDto);
        author.setName(authorNew.getName());
        ContentUpdateStatus status = author.getId() == null ?
                ContentUpdateStatus.CREATED_NEW : ContentUpdateStatus.UPDATED;
        return Pair.of(authorMapper.toAuthorDto(authorRepository.save(author)), status);
    }
}
