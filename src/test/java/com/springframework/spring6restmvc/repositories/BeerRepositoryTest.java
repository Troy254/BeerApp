package com.springframework.spring6restmvc.repositories;


import com.springframework.spring6restmvc.entities.Beer;
import com.springframework.spring6restmvc.model.BeerStyle;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@DataJpaTest
class BeerRepositoryTest {
    @Autowired
    BeerRepository beerRepository;

    @Test
    void TestSaveBeerNameTooLong() {

        assertThrows(ConstraintViolationException.class, () -> {
            Beer savedBeer = beerRepository.save(Beer.builder()
                    .beerName("MyBeereoprgeirgjbgjkbvvbxjbvkfhbdvfkudfdfgdfggdjvgcvjhdjdsgyyfggfjggvxcnbbnmxcbjvjgdczcggchtdydu")
                    .beerStyle(BeerStyle.PALE_ALE)
                    .price(new BigDecimal(11.35))
                    .quantityOnHand(100)
                    .upc("0199283392837438")
                    .build());
            beerRepository.flush();
        });
    }

    @Test
    void TestSaveBeer(){
        Beer savedBeer = beerRepository.save(Beer.builder()
                        .beerName("My Beer")
                        .beerStyle(BeerStyle.PALE_ALE)
                        .price(new BigDecimal(11.35))
                        .quantityOnHand(100)
                        .upc("0199283392837438")
                 .build());
       beerRepository.flush();
        assertThat(savedBeer).isNotNull();
        assertThat(savedBeer.getId()).isNotNull();
    }

}