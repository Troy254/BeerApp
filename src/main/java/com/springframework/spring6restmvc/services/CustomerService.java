package com.springframework.spring6restmvc.services;

import com.springframework.spring6restmvc.models.Customer;

import java.util.List;
import java.util.UUID;

public interface CustomerService {
    List<Customer> customerList();

    Customer getCustomerById(UUID id);

    Customer saveNewCustomer(Customer customer);

    void upDateCustomerById(UUID customerId, Customer customer);

    void deleteCustomerById(UUID customerId);
}
