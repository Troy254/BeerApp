package com.springframework.spring6restmvc.services;

import com.springframework.spring6restmvc.model.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {
    private Map<UUID, Customer> customerMap;

    public CustomerServiceImpl() {
        this.customerMap = new HashMap<>();

        Customer customer1 = Customer.builder()
                .id(UUID.randomUUID())
                .firstName("James")
                .lastName("Bond")
                .phoneNumber("+14132635363366")
                .build();


        Customer customer2 = Customer.builder()
                .id(UUID.randomUUID())
                .firstName("Bruce")
                .lastName("Willis")
                .phoneNumber("+1322035363366")
                .build();

        Customer customer3 = Customer.builder()
                .id(UUID.randomUUID())
                .firstName("Jackie")
                .lastName("Chan")
                .phoneNumber("+100000363366")
                .build();
        customerMap.put(customer1.getId(), customer1);
        customerMap.put(customer2.getId(), customer2);
        customerMap.put(customer3.getId(), customer3);
    }


    @Override
     public List<Customer> listCustomers(){
        return new ArrayList<>(customerMap.values());
    }

    @Override
    public Customer getCustomerById(UUID id) {
        log.debug("Get Customer By Id - in Service " + id.toString());
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
    public void updateCustomerById(UUID customerId, Customer customer) {
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
