package com.springframework.spring6restmvc.services;

import com.springframework.spring6restmvc.model.CustomerDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {
    private Map<UUID, CustomerDTO> customerMap;

    public CustomerServiceImpl() {
        this.customerMap = new HashMap<>();

        CustomerDTO customer1 = CustomerDTO.builder()
                .id(UUID.randomUUID())
                .version(1)
                .firstName("James")
                .lastName("Bond")
                .phoneNumber("+14132635363366")
                .build();


        CustomerDTO customer2 = CustomerDTO.builder()
                .id(UUID.randomUUID())
                .version(1)
                .firstName("Bruce")
                .lastName("Willis")
                .phoneNumber("+1322035363366")
                .build();

        CustomerDTO customer3 = CustomerDTO.builder()
                .id(UUID.randomUUID())
                .version(1)
                .firstName("Jackie")
                .lastName("Chan")
                .phoneNumber("+100000363366")
                .build();
        customerMap.put(customer1.getId(), customer1);
        customerMap.put(customer2.getId(), customer2);
        customerMap.put(customer3.getId(), customer3);
    }


    @Override
     public List<CustomerDTO> listCustomers(){

        return new ArrayList<>(customerMap.values());
    }

    @Override
    public Optional<CustomerDTO> getCustomerById(UUID id) {
        log.debug("Get Customer By Id - in Service " + id.toString());
        return Optional.ofNullable(customerMap.get(id));
    }

    @Override
    public CustomerDTO saveNewCustomer(CustomerDTO customer) {
        CustomerDTO savedCustomer = CustomerDTO.builder()
                .id(UUID.randomUUID())
                .version(customer.getVersion())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .phoneNumber(customer.getPhoneNumber())
                .build();
        customerMap.put(savedCustomer.getId(),savedCustomer);
        return savedCustomer;
    }


    @Override
    public Optional<CustomerDTO> updateCustomerById(UUID customerId, CustomerDTO customer) {
        CustomerDTO existing = customerMap.get(customerId);
                existing.setFirstName(customer.getFirstName());
                existing.setLastName(customer.getLastName());
                existing.setPhoneNumber(customer.getPhoneNumber());
                customerMap.put(existing.getId(),existing);
        return Optional.of(existing);
    }


    @Override
    public void deleteCustomerById(UUID customerId) {
        customerMap.remove(customerId);
    }
}
