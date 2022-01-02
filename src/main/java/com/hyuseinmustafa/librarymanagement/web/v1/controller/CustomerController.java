package com.hyuseinmustafa.librarymanagement.web.v1.controller;

import com.hyuseinmustafa.librarymanagement.service.ContentUpdateStatus;
import com.hyuseinmustafa.librarymanagement.service.CustomerService;
import com.hyuseinmustafa.librarymanagement.web.v1.model.GetCustomerDto;
import com.hyuseinmustafa.librarymanagement.web.v1.model.PostCustomerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping
    public ResponseEntity<List<GetCustomerDto>> getAllCustomers(){
        return new ResponseEntity(customerService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetCustomerDto> getCustomer(@PathVariable Long id){
        return new ResponseEntity(customerService.getById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<GetCustomerDto> createNewCustomer(@RequestBody @Valid PostCustomerDto postCustomerDto){
        return new ResponseEntity(customerService.create(postCustomerDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GetCustomerDto> updateCustomer(@PathVariable Long id,
                                                 @RequestBody @Valid PostCustomerDto postCustomerDto){
        Pair pair = customerService.update(id, postCustomerDto);
        return new ResponseEntity(pair.getFirst(),
                (pair.getSecond() == ContentUpdateStatus.UPDATED) ? HttpStatus.OK : HttpStatus.CREATED);
    }
}
