package com.hyuseinmustafa.librarymanagement.web.v1.controller;

import com.hyuseinmustafa.librarymanagement.service.AuthorService;
import com.hyuseinmustafa.librarymanagement.service.BookCopyService;
import com.hyuseinmustafa.librarymanagement.service.ContentUpdateStatus;
import com.hyuseinmustafa.librarymanagement.web.v1.model.GetAuthorDto;
import com.hyuseinmustafa.librarymanagement.web.v1.model.GetBookCopyDto;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.util.Pair;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(BookCopyController.class)
class BookCopyControllerTest {

    @MockBean
    BookCopyService service;

    @Autowired
    MockMvc mockMvc;

    private static final String URI = "/api/v1/bookcopies";
    private static final String JSON_CONTENT = "{\"bookId\":1,\"locationId\":1}";
    private final GetBookCopyDto dto1 = GetBookCopyDto.builder().build();
    private final GetBookCopyDto dto2 = GetBookCopyDto.builder().build();
    private final ResultMatcher[] matchers = {
            jsonPath("$.id").hasJsonPath(),
            jsonPath("$.available").hasJsonPath(),
            jsonPath("$.book").hasJsonPath(),
            jsonPath("$.location").hasJsonPath(),
            jsonPath("$.customer").hasJsonPath()
    };

    @Test
    void getAllBookCopies() throws Exception {
        Mockito.when(service.getAll()).thenReturn(Arrays.asList(dto1, dto2));

        mockMvc.perform(get(URI).accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)));
    }

    @Test
    void getBookCopy() throws Exception {
        Mockito.when(service.getById(anyLong())).thenReturn(dto1);

        mockMvc.perform(get(URI + "/1").accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpectAll(matchers);
    }

    @Test
    void createBookCopy() throws Exception {
        Mockito.when(service.create(any())).thenReturn(dto1);

        mockMvc.perform(post(URI).accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON_CONTENT))
                .andExpect(status().isCreated())
                .andExpectAll(matchers);
    }

    @Test
    void updateBookCopy() throws Exception {
        Mockito.when(service.update(anyLong(), any()))
                .thenAnswer(invocationOnMock -> {
                    if(invocationOnMock.getArgument(0, Long.class) == 1)
                        return Pair.of(dto1, ContentUpdateStatus.UPDATED);
                    else return Pair.of(dto2, ContentUpdateStatus.CREATED_NEW);
                });

        mockMvc.perform(put(URI + "/1").accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON_CONTENT))
                .andExpect(status().isOk())
                .andExpectAll(matchers);

        mockMvc.perform(put(URI + "/2").accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON_CONTENT))
                .andExpect(status().isCreated())
                .andExpectAll(matchers);
    }

    @Test
    void borrowBookCopy() {
    }

    @Test
    void returnBookCopy() {
    }
}