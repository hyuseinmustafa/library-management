package com.hyuseinmustafa.librarymanagement.service;

import com.hyuseinmustafa.librarymanagement.domain.Book;
import com.hyuseinmustafa.librarymanagement.domain.BookCopy;
import com.hyuseinmustafa.librarymanagement.domain.Customer;
import com.hyuseinmustafa.librarymanagement.domain.Location;
import com.hyuseinmustafa.librarymanagement.exception.BookCopyBorrowedException;
import com.hyuseinmustafa.librarymanagement.exception.NotFoundException;
import com.hyuseinmustafa.librarymanagement.repository.BookCopyRepository;
import com.hyuseinmustafa.librarymanagement.repository.BookRepository;
import com.hyuseinmustafa.librarymanagement.repository.CustomerRepository;
import com.hyuseinmustafa.librarymanagement.repository.LocationRepository;
import com.hyuseinmustafa.librarymanagement.web.v1.mapper.BookCopyMapper;
import com.hyuseinmustafa.librarymanagement.web.v1.mapper.BookCopyMapperImpl;
import com.hyuseinmustafa.librarymanagement.web.v1.model.GetBookCopyDto;
import com.hyuseinmustafa.librarymanagement.web.v1.model.PostBookCopyDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.util.Pair;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(MockitoExtension.class)
class BookCopyServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private LocationRepository locationRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private BookCopyRepository repository;
    private BookCopyService service;
    private final Book book = Book.builder().id(1L).name("Book 1").isbn("55-63-887-1").build();
    private final Customer customer = Customer.builder().id(1L).name("Customer Name").address("Address").build();
    private final Location location = Location.builder().id(1L).roomNumber(1L).shelfNumber(1L).build();
    private final BookCopy bookCopy = BookCopy.builder().id(1L).book(book)
            .customer(customer).location(location).available(true).build();
    private final PostBookCopyDto dto = PostBookCopyDto.builder().bookId(1L).locationId(1L).build();

    @BeforeEach
    void setUp() {
        BookCopyMapper mapper = new BookCopyMapperImpl();
        service = new BookCopyServiceImpl(repository, mapper, bookRepository, locationRepository, customerRepository);
    }

    @Test
    void getAll() {
        Mockito.when(repository.findAll()).thenReturn(Collections.singletonList(bookCopy));

        List<GetBookCopyDto> response = service.getAll();

        assertEquals(response.size(), 1);
    }

    @Test
    void getById() {
        Assertions.assertThrowsExactly(NotFoundException.class, () -> service.getById(1L));

        Mockito.when(repository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(bookCopy));

        GetBookCopyDto response = service.getById(1L);

        assertThat(response).isNotNull();
    }

    @Test
    void create() {
        Mockito.when(bookRepository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(book));
        Mockito.when(locationRepository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(location));
        Mockito.when(repository.save(any(BookCopy.class))).thenReturn(bookCopy);

        GetBookCopyDto response = service.create(dto);

        assertThat(response).isNotNull();
    }

    @Test
    void update() {
        Mockito.when(repository.save(any(BookCopy.class))).thenReturn(bookCopy);
        Mockito.when(repository.findById(1L)).thenReturn(java.util.Optional.ofNullable(bookCopy));
        Mockito.when(repository.findById(2L)).thenReturn(java.util.Optional.empty());

        Mockito.when(bookRepository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(book));
        Mockito.when(locationRepository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(location));
        Pair<GetBookCopyDto, ContentUpdateStatus> response = service.update(1L, dto);

        assertThat(response.getFirst().getBook().getId()).isEqualTo(book.getId());
        assertThat(response.getFirst().getCustomer().getId()).isEqualTo(customer.getId());
        assertThat(response.getFirst().getLocation().getId()).isEqualTo(location.getId());

        response = service.update(2L, dto);

        assertThat(response.getFirst().getBook().getId()).isEqualTo(book.getId());
        assertThat(response.getFirst().getCustomer().getId()).isEqualTo(customer.getId());
        assertThat(response.getFirst().getLocation().getId()).isEqualTo(location.getId());
    }

    @Test
    void borrow() {
        Mockito.when(repository.save(any(BookCopy.class))).thenReturn(bookCopy);
        Mockito.when(repository.findById(1L)).thenReturn(java.util.Optional.ofNullable(bookCopy));
        Mockito.when(customerRepository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(customer));

        GetBookCopyDto response = service.borrow(1L, 1L);
        Assertions.assertThrowsExactly(BookCopyBorrowedException.class, () -> service.borrow(1L, 1L));

        assertThat(response.getBook().getId()).isEqualTo(book.getId());
    }

    @Test
    void rturn() {
        Mockito.when(repository.save(any(BookCopy.class))).thenReturn(bookCopy);
        Mockito.when(repository.findById(1L)).thenReturn(java.util.Optional.ofNullable(bookCopy));

        GetBookCopyDto response = service.rturn(1L);

        assertThat(response.getBook().getId()).isEqualTo(book.getId());
    }
}