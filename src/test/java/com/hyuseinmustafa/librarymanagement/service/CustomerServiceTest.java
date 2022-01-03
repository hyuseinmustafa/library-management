package com.hyuseinmustafa.librarymanagement.service;

import com.hyuseinmustafa.librarymanagement.domain.Customer;
import com.hyuseinmustafa.librarymanagement.exception.NotFoundException;
import com.hyuseinmustafa.librarymanagement.repository.CustomerRepository;
import com.hyuseinmustafa.librarymanagement.web.v1.mapper.CustomerMapper;
import com.hyuseinmustafa.librarymanagement.web.v1.mapper.CustomerMapperImpl;
import com.hyuseinmustafa.librarymanagement.web.v1.model.GetCustomerDto;
import com.hyuseinmustafa.librarymanagement.web.v1.model.PostCustomerDto;
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
class CustomerServiceTest {

    @Mock
    private CustomerRepository repository;
    private CustomerService service;
    private final Customer customer = Customer.builder().id(1L).name("Customer 1").address("Address 1").build();
    private final PostCustomerDto dto = PostCustomerDto.builder().name("Dto Name").address("Dto Address").build();

    @BeforeEach
    void setUp() {
        CustomerMapper mapper = new CustomerMapperImpl();
        service = new CustomerServiceImpl(repository, mapper);
    }

    @Test
    void getAll() {
        Mockito.when(repository.findAll()).thenReturn(Collections.singletonList(customer));

        List<GetCustomerDto> response = service.getAll();

        assertEquals(response.size(), 1);
    }

    @Test
    void getById() {
        Assertions.assertThrowsExactly(NotFoundException.class, () -> service.getById(1L));

        Mockito.when(repository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(customer));

        GetCustomerDto response = service.getById(1L);

        assertThat(response).isNotNull();
    }

    @Test
    void create() {
        Mockito.when(repository.save(any(Customer.class))).thenReturn(customer);

        GetCustomerDto response = service.create(dto);

        assertThat(response).isNotNull();
    }

    @Test
    void update() {
        Mockito.when(repository.save(any(Customer.class))).thenReturn(customer);
        Mockito.when(repository.findById(1L)).thenReturn(java.util.Optional.ofNullable(customer));
        Mockito.when(repository.findById(2L)).thenReturn(java.util.Optional.empty());

        Pair<GetCustomerDto, ContentUpdateStatus> response = service.update(1L, dto);

        assertThat(response.getFirst().getName()).isEqualTo(dto.getName());

        response = service.update(2L, dto);

        assertThat(response.getFirst().getName()).isEqualTo(dto.getName());
    }
}