package com.hyuseinmustafa.librarymanagement.service;

import com.hyuseinmustafa.librarymanagement.domain.Author;
import com.hyuseinmustafa.librarymanagement.exception.NotFoundException;
import com.hyuseinmustafa.librarymanagement.repository.AuthorRepository;
import com.hyuseinmustafa.librarymanagement.web.v1.mapper.AuthorMapper;
import com.hyuseinmustafa.librarymanagement.web.v1.mapper.AuthorMapperImpl;
import com.hyuseinmustafa.librarymanagement.web.v1.model.GetAuthorDto;
import com.hyuseinmustafa.librarymanagement.web.v1.model.PostAuthorDto;
import org.junit.jupiter.api.AfterEach;
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
class AuthorServiceTest {

    @Mock
    private AuthorRepository repository;
    private AuthorService service;
    private final Author author1 = Author.builder().id(1L).name("Author 1").build();

    @BeforeEach
    void setUp() {
        AuthorMapper mapper = new AuthorMapperImpl();
        service = new AuthorServiceImpl(repository, mapper);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testGetAll() {
        Mockito.when(repository.findAll()).thenReturn(Collections.singletonList(author1));

        List<GetAuthorDto> response = service.getAll();

        assertEquals(response.size(), 1);
    }

    @Test
    void testGetById() {
        Assertions.assertThrowsExactly(NotFoundException.class, () -> service.getById(1L));

        Mockito.when(repository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(author1));

        GetAuthorDto response = service.getById(1L);

        assertThat(response).isNotNull();
    }

    @Test
    void testCreate() {
        PostAuthorDto dto = PostAuthorDto.builder().build();
        Mockito.when(repository.save(any(Author.class))).thenReturn(author1);

        GetAuthorDto response = service.create(dto);

        assertThat(response).isNotNull();
    }

    @Test
    void testUpdate() {
        PostAuthorDto dto = PostAuthorDto.builder().name("Updated Name").build();
        Mockito.when(repository.save(any(Author.class))).thenReturn(author1);
        Mockito.when(repository.findById(1L)).thenReturn(java.util.Optional.ofNullable(author1));
        Mockito.when(repository.findById(2L)).thenReturn(java.util.Optional.empty());

        Pair<GetAuthorDto, ContentUpdateStatus> response = service.update(1L, dto);

        assertThat(response.getFirst().getName()).isEqualTo(dto.getName());

        response = service.update(2L, dto);

        assertThat(response.getFirst().getName()).isEqualTo(dto.getName());
    }
}