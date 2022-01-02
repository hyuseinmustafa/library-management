package com.hyuseinmustafa.librarymanagement.service;

import com.hyuseinmustafa.librarymanagement.domain.Author;
import com.hyuseinmustafa.librarymanagement.domain.Book;
import com.hyuseinmustafa.librarymanagement.exception.NotFoundException;
import com.hyuseinmustafa.librarymanagement.repository.AuthorRepository;
import com.hyuseinmustafa.librarymanagement.repository.BookRepository;
import com.hyuseinmustafa.librarymanagement.web.v1.mapper.BookMapper;
import com.hyuseinmustafa.librarymanagement.web.v1.model.GetBookDto;
import com.hyuseinmustafa.librarymanagement.web.v1.model.PostBookDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final AuthorRepository authorRepository;

    @Override
    public List<GetBookDto> getAll() {
        return StreamSupport.stream(bookRepository.findAll().spliterator(), false)
                .map(bookMapper::toGetBookDto).collect(Collectors.toList());
    }

    @Override
    public GetBookDto getById(Long id) {
        return bookMapper.toGetBookDto(bookRepository.findById(id).orElseThrow(NotFoundException::new));
    }

    @Override
    public GetBookDto createNew(PostBookDto postBookDto) {
        Book book = bookMapper.toBook(postBookDto);
        Set<Author> authors = getAllAuthors(postBookDto.getAuthorIds());
        book.setAuthors(authors);
        return bookMapper.toGetBookDto(bookRepository.save(book));
    }

    @Override
    public Pair<GetBookDto, ContentUpdateStatus> update(Long id, PostBookDto postBookDto) {
        Book book = bookRepository.findById(id).orElse(new Book());
        Set<Author> authors = getAllAuthors(postBookDto.getAuthorIds());
        Book newBook = bookMapper.toBook(postBookDto);
        book.setName(newBook.getName());
        book.setIsbn(newBook.getIsbn());
        book.setAuthors(authors);
        ContentUpdateStatus status = (book.getId() == null) ?
                ContentUpdateStatus.CREATED_NEW : ContentUpdateStatus.UPDATED;
        return Pair.of(bookMapper.toGetBookDto(bookRepository.save(book)), status);
    }

    private Set<Author> getAllAuthors(Set<Long> ids){
        Set<Author> authors = StreamSupport
                .stream(authorRepository.findAllById(ids).spliterator(), false)
                .collect(Collectors.toSet());
        if(authors.size() != ids.size())//checks for all authors are found
            throw new RuntimeException("Author not found, Implement better functionality");
        return authors;
    }
}
