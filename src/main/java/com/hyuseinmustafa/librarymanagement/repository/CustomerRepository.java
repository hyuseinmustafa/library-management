package com.hyuseinmustafa.librarymanagement.repository;

import com.hyuseinmustafa.librarymanagement.domain.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
}
