package com.springframework.spring6restmvc.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springframework.spring6restmvc.entities.Beer;
import com.springframework.spring6restmvc.mapper.BeerMapper;
import com.springframework.spring6restmvc.model.BeerDTO;
import com.springframework.spring6restmvc.model.BeerStyle;
import com.springframework.spring6restmvc.repositories.BeerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
class BeerControllerIT {

    @Autowired
    BeerController beerController;

    @Autowired
    BeerRepository beerRepository;

    @Autowired
    BeerMapper beerMapper;

    @Autowired
    ObjectMapper objectMapper;


    @Autowired
    WebApplicationContext wac;

   MockMvc mockMvc;

   @BeforeEach
   void setUp() {
       mockMvc = MockMvcBuilders.webAppContextSetup(wac)
               .apply(springSecurity())
               .build();
   }
    @Rollback
    @Transactional
    @Test
    void deleteByIdFound() {
        Beer beer = beerRepository.findAll().get(0);

        ResponseEntity responseEntity = beerController.deleteById(beer.getId());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));

        assertThat(beerRepository.findById(beer.getId()).isEmpty());
    }


    @Test
    void tesListBeersByName() throws Exception {
        mockMvc.perform(get(BeerController.BEER_PATH)
                        .with(httpBasic(BeerControllerTest.USERNAME,BeerControllerTest.PASSWORD))
                        .queryParam("beerName", "IPA"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(336)));
    }


    @Test
   void testUpdateNotFound(){
         assertThrows(NotFoundException.class,() -> {
            beerController.updateById(UUID.randomUUID(), BeerDTO.builder().build());
        });
    }


    @Rollback
    @Transactional
    @Test
    void updateExistingBeer() {
        Beer beer = beerRepository.findAll().get(0);
        BeerDTO beerDTO = beerMapper.beerToBeerDto(beer);
        beerDTO.setId(null);
        beerDTO.setVersion(null);
        final String beerName = "UPDATED";
        beerDTO.setBeerName(beerName);

        ResponseEntity responseEntity = beerController.updateById(beer.getId(), beerDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));

        Beer updatedBeer = beerRepository.findById(beer.getId()).get();
        assertThat(updatedBeer.getBeerName()).isEqualTo(beerName);
    }

    @Transactional
    @Rollback
    @Test
    void saveNewBeerTest() throws Exception {
        // Create a BeerDTO object to represent the request body
        Beer newBeer = new Beer();
        newBeer.setBeerName("Test Beer");
        newBeer.setBeerStyle(BeerStyle.PALE_ALE);
        newBeer.setPrice(new BigDecimal("9.99"));
        newBeer.setQuantityOnHand(50);
        newBeer.setUpc("1234567890");

        // Convert the BeerDTO to JSON
        String beerJson = objectMapper.writeValueAsString(newBeer);

        // Perform a POST request to create a new beer
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/beer")
                        .with(httpBasic(BeerControllerTest.USERNAME,BeerControllerTest.PASSWORD))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(beerJson))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.header().exists("Location"));
    }

    @Test
    void testBeerIdNotFound() {
        try {
            beerController.getBeerById(UUID.randomUUID());
        } catch (NotFoundException x) {
            throw x;
        }
    }

    @Test
    void testGetById(){
        Beer beer = beerRepository.findAll().get(0);
        Optional<BeerDTO> dto = beerController.getBeerById(beer.getId());
        assertThat(dto).isNotNull();
    }

    @Test
    void testListBeers() {
        List<BeerDTO> dtos = beerController.listBeers(null);
        assertThat(dtos.size()).isEqualTo(2413);
    }



    @Rollback
    @Transactional
    @Test
    void testEmptyList(){
        beerRepository.deleteAll();
        List<BeerDTO> dtos = beerController.listBeers(null);
        assertThat(dtos.size()).isEqualTo(0);
    }
}