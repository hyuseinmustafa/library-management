package com.hyuseinmustafa.librarymanagement.web.v1.mapper;

import com.hyuseinmustafa.librarymanagement.domain.Customer;
import com.hyuseinmustafa.librarymanagement.web.v1.model.GetCustomerDto;
import com.hyuseinmustafa.librarymanagement.web.v1.model.PostCustomerDto;
import org.mapstruct.Mapper;

@Mapper
public interface CustomerMapper {

    GetCustomerDto toGetCustomerDto(Customer customer);

    Customer toCustomer(PostCustomerDto postCustomerDto);
}
