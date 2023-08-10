package com.springframework.spring6restmvc.services;

import com.springframework.spring6restmvc.model.BeerDTO;

import java.util.List;
import java.util.UUID;

public interface BeerService {
    List<BeerDTO> listBeers();

    BeerDTO getBeerById(UUID beerId);

    BeerDTO saveNewBeer(BeerDTO beer);

    void updateBeerById(UUID beerId, BeerDTO beer);

    void deleteBeerById(UUID beerId);
}
