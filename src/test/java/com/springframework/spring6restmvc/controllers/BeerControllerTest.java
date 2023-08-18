package com.springframework.spring6restmvc.controllers;

 import com.fasterxml.jackson.databind.ObjectMapper;
 import com.springframework.spring6restmvc.model.BeerDTO;
 import com.springframework.spring6restmvc.services.BeerService;
 import com.springframework.spring6restmvc.services.BeerServiceImpl;
 import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
 import org.mockito.ArgumentCaptor;
 import org.mockito.MockitoAnnotations;
 import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;
 import org.springframework.test.web.servlet.MvcResult;
 import org.springframework.test.web.servlet.ResultMatcher;
 import static org.hamcrest.Matchers.*;
 import static org.mockito.BDDMockito.*;

 import java.math.BigDecimal;
 import java.util.Optional;
 import java.util.UUID;

 import static org.assertj.core.api.Assertions.assertThat;
 import static org.mockito.Mockito.verify;
 import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
 import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
 import static org.mockito.ArgumentMatchers.any;
 import static org.mockito.BDDMockito.given;
 import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
 import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
 import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(BeerController.class)
class BeerControllerTest {
    @Autowired
    MockMvc mockMvc;
     @Autowired
     ObjectMapper objectMapper;

    @MockBean
    BeerService beerService;

     BeerServiceImpl beerServiceImpl;


    private BeerDTO sampleBeer;
     @BeforeEach
     void setUp() {
         beerServiceImpl = new BeerServiceImpl();

             MockitoAnnotations.openMocks(this);
             sampleBeer = BeerDTO.builder()
                     .id(UUID.randomUUID())
                     .beerName("Galaxy Cat")
                     .price(new BigDecimal("12.10"))
                     .quantityOnHand(100)
                     .build();
         }

       @Test
       void testCreateBeerNullBeerName() throws Exception {
         BeerDTO beerDTO = BeerDTO.builder().build();
         given(beerService.saveNewBeer(any(BeerDTO.class))).willReturn(beerServiceImpl.listBeers().get(0));
        MvcResult mvcResult = mockMvc.perform(post(BeerController.BEER_PATH)
                 .accept(MediaType.APPLICATION_JSON)
                 .contentType(MediaType.APPLICATION_JSON)
                 .content(objectMapper.writeValueAsString(beerDTO)))
                 .andExpect(status().isBadRequest())
                 .andExpect(jsonPath("$.length()", is(2)))
                 .andReturn();
           System.out.println(mvcResult.getResponse().getContentAsString());
       }


     @Test
     void testDeleteBeer() throws Exception {
         BeerDTO beer = beerServiceImpl.listBeers().get(0);

         mockMvc.perform(delete("/api/v1/beer/" + beer.getId())
                         .accept(MediaType.APPLICATION_JSON))
                 .andExpect(status().isNoContent());

          ArgumentCaptor<UUID> uuidArgumentCaptor = ArgumentCaptor.forClass(UUID.class);
         verify(beerService).deleteBeerById(uuidArgumentCaptor.capture());

         assertThat(beer.getId()).isEqualTo(uuidArgumentCaptor.getValue());
     }


     @Test
     void testUpdateBeer() throws Exception {
         BeerDTO beer = beerServiceImpl.listBeers().get(0);
         given(beerService.updateBeerById(any(),any())).willReturn(Optional.of(beer));
         mockMvc.perform(put("/api/v1/beer/" + beer.getId())
                 .accept(MediaType.APPLICATION_JSON)
                 .contentType(MediaType.APPLICATION_JSON)
                 .content(objectMapper.writeValueAsString(beer)))
                 .andExpect(status().isNoContent());

         verify(beerService).updateBeerById(any(UUID.class),any(BeerDTO.class));
     }


     @Test
     void testCreateNewBeer() throws Exception {
         BeerDTO beer = beerServiceImpl.listBeers().get(0);
         beer.setVersion(null);
         beer.setId(null);
         given(beerService.saveNewBeer(any(BeerDTO.class))).willReturn(beerServiceImpl.listBeers().get(1));
          mockMvc.perform(post("/api/v1/beer")
                         .accept(MediaType.APPLICATION_JSON)
                         .contentType(MediaType.APPLICATION_JSON)
                         .content(objectMapper.writeValueAsString(beer)))
                 .andExpect(status().isCreated())
                  .andExpect(header().exists("Location"));
     }


     @Test
      void testListBeers() throws Exception {
         given(beerService.listBeers()).willReturn(beerServiceImpl.listBeers());

         mockMvc.perform(get("/api/v1/beer")
                         .accept(MediaType.APPLICATION_JSON))
                 .andExpect(status().isOk())
                 .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                  .andExpect(jsonPath("$", hasSize(3)));

         // Verify that the beerService.listBeers() method was called
         verify(beerService, times(1)).listBeers();
     }


    @Test
    void getBeerByIdNotFound() throws Exception{
         given(beerService.getBeerById(any(UUID.class))).willThrow(NotFoundException.class);
         BeerDTO testBeer = beerServiceImpl.listBeers().get(0);
         mockMvc.perform(get("/api/v1/beer" + testBeer.getId()))
                 .andExpect(status().isNotFound());
    }




    @Test
    public void testGetBeerById() throws Exception {
        when(beerService.getBeerById(any(UUID.class))).thenReturn(Optional.ofNullable(sampleBeer));
        mockMvc.perform(get("/api/v1/beer/{beerId}", sampleBeer.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.beerName").value(sampleBeer.getBeerName()));
//                .andExpect(jsonPath("$.price").value(sampleBeer.getPrice()))
//                .andExpect(jsonPath("$.quantityOnHand").value(sampleBeer.getQuantityOnHand()));
        verify(beerService, times(1)).getBeerById(any(UUID.class));
    }
}