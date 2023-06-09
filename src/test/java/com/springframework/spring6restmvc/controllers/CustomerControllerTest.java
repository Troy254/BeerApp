package com.springframework.spring6restmvc.controllers;

import com.springframework.spring6restmvc.models.Beer;
import com.springframework.spring6restmvc.services.BeerServiceImpl;
import com.springframework.spring6restmvc.services.CustomerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CustomerService customerService;

    BeerServiceImpl beerServiceImpl = new BeerServiceImpl();

    @Test
    void getCustomerById() throws Exception {
        Beer testBeer = beerServiceImpl.listBeers().get(0);
        mockMvc.perform(get("api/v1/customer" + UUID.randomUUID())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}