package com.springframework.spring6restmvc.services;

import com.springframework.spring6restmvc.models.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {
    private Map<UUID, Customer> customerMap;

    public CustomerServiceImpl() {
        this.customerMap = new HashMap<>();

        Customer samuel = Customer.builder()
                .id(UUID.randomUUID())
                .firstName("Samuel")
                .lastName("King")
                .phoneNumber("254700123654")
                .build();

        Customer eve = Customer.builder()
                .id(UUID.randomUUID())
                .firstName("Eve")
                .lastName("Latifah")
                .phoneNumber("254723546987")
                .build();

        Customer roy = Customer.builder()
                .id(UUID.randomUUID())
                .firstName("Roy")
                .lastName("Wandaka")
                .phoneNumber("254750321456")
                .build();

        customerMap.put(samuel.getId(),samuel);
        customerMap.put(eve.getId(),eve);
        customerMap.put(roy.getId(),roy);
    }

    @Override
    public List<Customer> customerList(){

        return new ArrayList<>(customerMap.values());
    }

    @Override
    public Customer getCustomerById(UUID id) {
        log.debug("Get Customer By Id - Services" + id.toString());
        return customerMap.get(id);
    }
    @Override
    public Customer saveNewCustomer(Customer customer) {
        Customer savedCustomer = Customer.builder()
                .id(UUID.randomUUID())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .phoneNumber(customer.getPhoneNumber())
                .build();
        customerMap.put(savedCustomer.getId(),savedCustomer);
        return savedCustomer;
    }

    @Override
    public void upDateCustomerById(UUID customerId, Customer customer) {
        Customer existing = customerMap.get(customerId);
        existing.setFirstName(customer.getFirstName());
        existing.setLastName(customer.getLastName());
        existing.setPhoneNumber(customer.getPhoneNumber());
        customerMap.put(existing.getId(),existing);
    }

    @Override
    public void deleteCustomerById(UUID customerId) {
        customerMap.remove(customerId);
    }
}
