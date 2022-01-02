package com.hyuseinmustafa.librarymanagement.service;

import com.hyuseinmustafa.librarymanagement.Exception.NotFoundException;
import com.hyuseinmustafa.librarymanagement.domain.Book;
import com.hyuseinmustafa.librarymanagement.repository.BookRepository;
import com.hyuseinmustafa.librarymanagement.web.v1.mapper.BookMapper;
import com.hyuseinmustafa.librarymanagement.web.v1.model.BookDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Override
    public List<BookDto> getAll() {
        return StreamSupport.stream(bookRepository.findAll().spliterator(), false)
                .map(bookMapper::toBookDto).collect(Collectors.toList());
    }

    @Override
    public BookDto getById(Long id) {
        return bookMapper.toBookDto(bookRepository.findById(id).orElseThrow(NotFoundException::new));
    }

    @Override
    public BookDto createNew(BookDto bookDto) {
        return bookMapper.toBookDto(bookRepository.save(bookMapper.toBook(bookDto)));
    }

    @Override
    public Pair<BookDto, ContentUpdateStatus> update(Long id, BookDto bookDto) {
        Book book = bookRepository.findById(id).orElse(new Book());
        Book newBook = bookMapper.toBook(bookDto);
        book.setName(newBook.getName());
        book.setIsbn(newBook.getIsbn());
        ContentUpdateStatus status = (book.getId() == null) ?
                ContentUpdateStatus.CREATED_NEW : ContentUpdateStatus.UPDATED;
        return Pair.of(bookMapper.toBookDto(bookRepository.save(book)), status);
    }
}
