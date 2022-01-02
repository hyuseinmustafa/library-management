package com.hyuseinmustafa.librarymanagement.service;

import com.hyuseinmustafa.librarymanagement.domain.Customer;
import com.hyuseinmustafa.librarymanagement.exception.NotFoundException;
import com.hyuseinmustafa.librarymanagement.repository.CustomerRepository;
import com.hyuseinmustafa.librarymanagement.web.v1.mapper.CustomerMapper;
import com.hyuseinmustafa.librarymanagement.web.v1.model.GetCustomerDto;
import com.hyuseinmustafa.librarymanagement.web.v1.model.PostCustomerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RequiredArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    public List<GetCustomerDto> getAll() {
        return StreamSupport.stream(customerRepository.findAll().spliterator(), false)
                .map(customerMapper::toGetCustomerDto).collect(Collectors.toList());
    }

    @Override
    public GetCustomerDto getById(Long id) {
        return customerMapper.toGetCustomerDto(customerRepository.findById(id).orElseThrow(NotFoundException::new));
    }

    @Override
    public GetCustomerDto create(PostCustomerDto postCustomerDto) {
        return customerMapper.toGetCustomerDto(customerRepository.save(customerMapper.toCustomer(postCustomerDto)));
    }

    @Override
    public Pair<GetCustomerDto, ContentUpdateStatus> update(Long id, PostCustomerDto postCustomerDto) {
        Customer customer = customerRepository.findById(id).orElse(new Customer());
        Customer customerNew = customerMapper.toCustomer(postCustomerDto);
        customer.setName(customerNew.getName());
        customer.setAddress(customerNew.getAddress());
        ContentUpdateStatus status = customer.getId() == null ?
                ContentUpdateStatus.CREATED_NEW : ContentUpdateStatus.UPDATED;
        return Pair.of(customerMapper.toGetCustomerDto(customerRepository.save(customer)), status);
    }


}
