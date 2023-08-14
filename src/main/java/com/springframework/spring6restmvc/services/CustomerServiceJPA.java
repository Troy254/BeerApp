package com.springframework.spring6restmvc.services;

import com.springframework.spring6restmvc.mapper.CustomerMapper;
import com.springframework.spring6restmvc.model.CustomerDTO;
import com.springframework.spring6restmvc.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
@Primary
@RequiredArgsConstructor
@Service
public class CustomerServiceJPA implements CustomerService {
    private CustomerRepository customerRepository;
    private CustomerMapper customerMapper;
    @Override
    public List<CustomerDTO> listCustomers() {
        return null;
    }

    @Override
    public CustomerDTO getCustomerById(UUID id) {

        return null;
    }

    @Override
    public CustomerDTO saveNewCustomer(CustomerDTO customer) {

        return null;
    }

    @Override
    public void updateCustomerById(UUID customerId, CustomerDTO customer) {

    }

    @Override
    public void deleteCustomerById(UUID customerId) {

    }
}
