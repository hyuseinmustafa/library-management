package com.hyuseinmustafa.librarymanagement.web.v1.controller;

import com.hyuseinmustafa.librarymanagement.service.ContentUpdateStatus;
import com.hyuseinmustafa.librarymanagement.service.CustomerService;
import com.hyuseinmustafa.librarymanagement.web.v1.model.GetCustomerDto;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @MockBean
    CustomerService service;

    @Autowired
    MockMvc mockMvc;

    private static final String URI = "/api/v1/customers";
    private static final String JSON_CONTENT = "{\"name\": \"asd\",\"address\": \"asd\"}";
    private final GetCustomerDto dto1 = GetCustomerDto.builder().build();
    private final GetCustomerDto dto2 = GetCustomerDto.builder().build();
    private final ResultMatcher[] matchers = {
            jsonPath("$.id").hasJsonPath(),
            jsonPath("$.name").hasJsonPath(),
            jsonPath("$.address").hasJsonPath()
    };

    @Test
    void getAllCustomers() throws Exception {
        Mockito.when(service.getAll()).thenReturn(Arrays.asList(dto1, dto2));

        mockMvc.perform(get(URI).accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)));
    }

    @Test
    void getCustomer() throws Exception {
        Mockito.when(service.getById(anyLong())).thenReturn(dto1);

        mockMvc.perform(get(URI + "/1").accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpectAll(matchers);
    }

    @Test
    void createNewCustomer() throws Exception {
        Mockito.when(service.create(any())).thenReturn(dto1);

        mockMvc.perform(post(URI).accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON_CONTENT))
                .andExpect(status().isCreated())
                .andExpectAll(matchers);
    }

    @Test
    void updateCustomer() throws Exception {
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
}