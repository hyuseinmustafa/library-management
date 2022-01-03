package com.hyuseinmustafa.librarymanagement.service;

import com.hyuseinmustafa.librarymanagement.domain.Author;
import com.hyuseinmustafa.librarymanagement.domain.Book;
import com.hyuseinmustafa.librarymanagement.exception.NotFoundException;
import com.hyuseinmustafa.librarymanagement.repository.AuthorRepository;
import com.hyuseinmustafa.librarymanagement.repository.BookRepository;
import com.hyuseinmustafa.librarymanagement.web.v1.mapper.BookMapper;
import com.hyuseinmustafa.librarymanagement.web.v1.mapper.BookMapperImpl;
import com.hyuseinmustafa.librarymanagement.web.v1.model.GetBookDto;
import com.hyuseinmustafa.librarymanagement.web.v1.model.PostBookDto;
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
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private AuthorRepository authorRepository;
    @Mock
    private BookRepository repository;
    private BookService service;
    private final Book book = Book.builder().id(1L).name("Book 1").isbn("55-63-887-1").build();
    private final PostBookDto dto = PostBookDto.builder().name("Dto Name")
            .isbn("Dto ISBN").authorIds(Collections.singleton(1L)).build();
    private final Set<Author> authors = Collections.singleton(Author.builder().id(1L).name("Author 1").build());

    @BeforeEach
    void setUp() {
        BookMapper mapper = new BookMapperImpl();
        service = new BookServiceImpl(repository, mapper, authorRepository);
    }

    @Test
    void getAll() {
        Mockito.when(repository.findAll()).thenReturn(Collections.singletonList(book));

        List<GetBookDto> response = service.getAll();

        assertEquals(response.size(), 1);
    }

    @Test
    void getById() {
        Assertions.assertThrowsExactly(NotFoundException.class, () -> service.getById(1L));

        Mockito.when(repository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(book));

        GetBookDto response = service.getById(1L);

        assertThat(response).isNotNull();
    }

    @Test
    void create() {
        Mockito.when(authorRepository.findAllById(any(Iterable.class))).thenReturn(authors);
        Mockito.when(repository.save(any(Book.class))).thenReturn(book);

        GetBookDto response = service.create(dto);

        assertThat(response).isNotNull();
    }

    @Test
    void update() {
        Mockito.when(repository.save(any(Book.class))).thenReturn(book);
        Mockito.when(repository.findById(1L)).thenReturn(java.util.Optional.ofNullable(book));
        Mockito.when(repository.findById(2L)).thenReturn(java.util.Optional.empty());

        Assertions.assertThrowsExactly(RuntimeException.class, () -> service.update(1L, dto));
        Mockito.when(authorRepository.findAllById(any(Iterable.class))).thenReturn(authors);
        Pair<GetBookDto, ContentUpdateStatus> response = service.update(1L, dto);

        assertThat(response.getFirst().getName()).isEqualTo(dto.getName());
        assertThat(response.getFirst().getIsbn()).isEqualTo(dto.getIsbn());
        assertThat(response.getFirst().getAuthors().size()).isEqualTo(authors.size());

        response = service.update(2L, dto);

        assertThat(response.getFirst().getName()).isEqualTo(dto.getName());
        assertThat(response.getFirst().getIsbn()).isEqualTo(dto.getIsbn());
        assertThat(response.getFirst().getAuthors().size()).isEqualTo(authors.size());
    }
}