package com.springframework.spring6restmvc.services;

import com.springframework.spring6restmvc.models.Beer;
import com.springframework.spring6restmvc.models.BeerStyle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class BeerServiceImpl implements BeerService {
    private Map<UUID, Beer> beerMap;

    public BeerServiceImpl() {
        this.beerMap = new HashMap<>();
        Beer beer1 = Beer.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("Tusker")
                .beerStyle(BeerStyle.PALE_ALE)
                .upc("129387575735736")
                .quantityOnHand(200)
                .price(new BigDecimal("10.99"))
                .createDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        Beer beer2 = Beer.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("Pilsner")
                .beerStyle(BeerStyle.PALE_ALE)
                .upc("03003840847555545")
                .quantityOnHand(150)
                .price(new BigDecimal("9.99"))
                .createDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        Beer beer3 = Beer.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("Guiness")
                .beerStyle(BeerStyle.PALE_ALE)
                .upc("7663838695745765458")
                .quantityOnHand(150)
                .price(new BigDecimal("12.99"))
                .createDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        beerMap.put(beer1.getId(), beer1);
        beerMap.put(beer2.getId(), beer2);
        beerMap.put(beer3.getId(), beer3);
    }

    @Override
    public List<Beer> listBeers() {
        return new ArrayList<>(beerMap.values());
    }

    @Override
    public Beer getBeerById(UUID id) {
        log.debug("Get Beer By Id - Service");
        return beerMap.get(id);
    }

    @Override
    public Beer saveNewBeer(Beer beer) {
        Beer savedBeer = Beer.builder()
                .id(UUID.randomUUID())
                .version(beer.getVersion())
                .beerName(beer.getBeerName())
                .beerStyle(beer.getBeerStyle())
                .upc(beer.getUpc())
                .quantityOnHand(beer.getQuantityOnHand())
                .price(beer.getPrice())
                .createDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();
        beerMap.put(savedBeer.getId(),savedBeer);
        return savedBeer;
    }

    @Override
    public void updateBeerById(Beer beer, UUID beerId) {
        Beer existing = beerMap.get(beerId);
        existing.setVersion(beer.getVersion());
        existing.setBeerName(beer.getBeerName());
        existing.setUpc(beer.getUpc());
        existing.setQuantityOnHand(beer.getQuantityOnHand());
        existing.setPrice(beer.getPrice());
        existing.setCreateDate(LocalDateTime.now());
        existing.setUpdateDate(LocalDateTime.now());
        beerMap.put(existing.getId(),existing);

    }

    @Override
    public void deleteBeerById(UUID beerId) {
        beerMap.remove(beerId);
    }


}