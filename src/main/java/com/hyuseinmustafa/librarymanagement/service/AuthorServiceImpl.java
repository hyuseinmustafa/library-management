package com.hyuseinmustafa.librarymanagement.service;

import com.hyuseinmustafa.librarymanagement.Exception.NotFoundException;
import com.hyuseinmustafa.librarymanagement.domain.Author;
import com.hyuseinmustafa.librarymanagement.repository.AuthorRepository;
import com.hyuseinmustafa.librarymanagement.web.v1.mapper.AuthorMapper;
import com.hyuseinmustafa.librarymanagement.web.v1.model.AuthorDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AuthorServiceImpl implements AuthorService{

    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    @Override
    public List<AuthorDto> getAll() {
        List<AuthorDto> authors = new ArrayList<>();
        authorRepository.findAll().forEach(author -> authors.add(authorMapper.toAuthorDto(author)));
        if(authors.isEmpty())throw new NotFoundException();
        return authors;
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
    public AuthorDto updateAuthor(Long id, AuthorDto authorDto) {
        Author author = authorRepository.findById(id).orElseThrow(() -> new NotFoundException());
        Author authorNew = authorMapper.toAuthor(authorDto);
        author.setName(authorNew.getName());
        return authorMapper.toAuthorDto(authorRepository.save(author));
    }
}
