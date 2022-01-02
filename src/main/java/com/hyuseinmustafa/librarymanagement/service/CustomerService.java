package com.hyuseinmustafa.librarymanagement.service;

import com.hyuseinmustafa.librarymanagement.web.v1.model.GetCustomerDto;
import com.hyuseinmustafa.librarymanagement.web.v1.model.PostCustomerDto;
import org.springframework.data.util.Pair;

import java.util.List;

public interface CustomerService {
    List<GetCustomerDto> getAll();
    GetCustomerDto getById(Long id);
    GetCustomerDto create(PostCustomerDto postCustomerDto);
    Pair<GetCustomerDto, ContentUpdateStatus> update(Long id, PostCustomerDto postCustomerDto);
}
