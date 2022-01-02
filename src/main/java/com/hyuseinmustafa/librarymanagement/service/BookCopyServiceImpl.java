package com.hyuseinmustafa.librarymanagement.service;

import com.hyuseinmustafa.librarymanagement.domain.BookCopy;
import com.hyuseinmustafa.librarymanagement.domain.Customer;
import com.hyuseinmustafa.librarymanagement.exception.BookCopyBorrowedException;
import com.hyuseinmustafa.librarymanagement.exception.NotFoundException;
import com.hyuseinmustafa.librarymanagement.repository.BookCopyRepository;
import com.hyuseinmustafa.librarymanagement.repository.BookRepository;
import com.hyuseinmustafa.librarymanagement.repository.CustomerRepository;
import com.hyuseinmustafa.librarymanagement.repository.LocationRepository;
import com.hyuseinmustafa.librarymanagement.web.v1.mapper.BookCopyMapper;
import com.hyuseinmustafa.librarymanagement.web.v1.model.GetBookCopyDto;
import com.hyuseinmustafa.librarymanagement.web.v1.model.PostBookCopyDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RequiredArgsConstructor
@Service
public class BookCopyServiceImpl implements BookCopyService {

    private final BookCopyRepository bookCopyRepository;
    private final BookCopyMapper bookCopyMapper;
    private final BookRepository bookRepository;
    private final LocationRepository locationRepository;
    private final CustomerRepository customerRepository;

    @Override
    public List<GetBookCopyDto> getAll() {
        return StreamSupport.stream(bookCopyRepository.findAll().spliterator(), false)
                .map(bookCopyMapper::toGetBookCopyDto).collect(Collectors.toList());
    }

    @Override
    public GetBookCopyDto getById(Long id) {
        return bookCopyMapper.toGetBookCopyDto(bookCopyRepository.findById(id).orElseThrow(NotFoundException::new));
    }

    @Override
    public GetBookCopyDto create(PostBookCopyDto postBookCopyDto) {
        BookCopy bookCopy = new BookCopy();
        bookCopy.setBook(bookRepository.findById(postBookCopyDto.getBookId()).orElseThrow(NotFoundException::new));
        bookCopy.setLocation(locationRepository.findById(postBookCopyDto.getLocationId())
                .orElseThrow(NotFoundException::new));
        bookCopy.setAvailable(true);
        return bookCopyMapper.toGetBookCopyDto(bookCopyRepository.save(bookCopy));
    }

    @Override
    public Pair<GetBookCopyDto, ContentUpdateStatus> update(Long id, PostBookCopyDto postBookCopyDto) {
        BookCopy bookCopy = bookCopyRepository.findById(id).orElse(new BookCopy());
        bookCopy.setBook(bookRepository.findById(postBookCopyDto.getBookId()).orElseThrow(NotFoundException::new));
        bookCopy.setLocation(locationRepository.findById(postBookCopyDto.getLocationId())
                .orElseThrow(NotFoundException::new));
        ContentUpdateStatus status = bookCopy.getId() == null ?
                ContentUpdateStatus.CREATED_NEW : ContentUpdateStatus.UPDATED;
        return Pair.of(bookCopyMapper.toGetBookCopyDto(bookCopyRepository.save(bookCopy)), status);
    }

    @Override
    public GetBookCopyDto borrow(Long id, Long customerId) {
        BookCopy bookCopy = bookCopyRepository.findById(id).orElseThrow(NotFoundException::new);
        if(bookCopy.getAvailable() == false) throw new BookCopyBorrowedException();
        bookCopy.setAvailable(false);
        Customer customer = customerRepository.findById(customerId).orElseThrow(NotFoundException::new);
        bookCopy.setCustomer(customer);
        return bookCopyMapper.toGetBookCopyDto(bookCopyRepository.save(bookCopy));
    }

    @Override
    public GetBookCopyDto rturn(Long id) {
        BookCopy bookCopy = bookCopyRepository.findById(id).orElseThrow(NotFoundException::new);
        bookCopy.setAvailable(true);
        bookCopy.setCustomer(null);
        return bookCopyMapper.toGetBookCopyDto(bookCopyRepository.save(bookCopy));
    }
}
