package com.springframework.spring6restmvc.controllers;

import com.springframework.spring6restmvc.services.BeerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.SQLOutput;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class CustomerControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    BeerService beerService;

    @Test
    void getCustomerById() {
        System.out.println(beerService.getBeerById(UUID.randomUUID()));
    }

    @Test
    void deleteById() {
    }

    @Test
    void updateById() {
    }

    @Test
    void postHandle() {
    }

    @Test
    void listCustomers() {
    }

    @Test
    void testGetCustomerById() {
    }
}