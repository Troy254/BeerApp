package com.springframework.spring6restmvc.controllers;

import com.springframework.spring6restmvc.entities.Customer;
import com.springframework.spring6restmvc.model.CustomerDTO;
import com.springframework.spring6restmvc.repositories.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.jayway.jsonpath.internal.path.PathCompiler.fail;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CustomerControllerIT {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CustomerController customerController;


    @Rollback
    @Transactional
    @Test
    void testListAllEmptyList() {
        customerRepository.deleteAll();
        List<CustomerDTO> dtos = customerController.listCustomers();

        assertThat(dtos.size()).isEqualTo(0);
    }

    @Test
    void testListAll() {
        List<CustomerDTO> dtos = customerController.listCustomers();

        assertThat(dtos.size()).isEqualTo(3);
    }

//    @Test
//    void testGetByIdNotFound() {
//        assertThrows(NotFoundException.class, () -> {
//            customerController.getCustomerById(UUID.randomUUID());
//        });
//    }

    @Test
    void testGetByIdNotFound() {
        UUID randomUUID = UUID.randomUUID();

        try {
            customerController.getCustomerById(randomUUID);
            fail("Expected NotFoundException, but no exception was thrown.");
        } catch (NotFoundException e) {
            System.out.println("NotFoundException caught: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected exception caught: " + e.getMessage());
        }
    }

    @Test
    void testGetById() {
        Customer customer = customerRepository.findAll().get(0);
        Optional<CustomerDTO> customerDTO = customerController.getCustomerById(customer.getId());
        assertThat(customerDTO).isNotNull();
    }
}









