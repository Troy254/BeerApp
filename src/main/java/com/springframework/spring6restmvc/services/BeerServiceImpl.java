package com.springframework.spring6restmvc.services;

 import com.springframework.spring6restmvc.model.BeerDTO;
import com.springframework.spring6restmvc.model.BeerStyle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class BeerServiceImpl implements BeerService {

    private Map<UUID, BeerDTO> beerMap;

    public BeerServiceImpl() {
        this.beerMap = new HashMap<>();

        BeerDTO beer1 = BeerDTO.builder()
                .id(UUID.randomUUID())
                .version(1)
                 .beerName("Galaxy Cat")
                .beerStyle(BeerStyle.PALE_ALE)
                 .upc("784873478494959")
                .price(new BigDecimal("10.99"))
                 .quantityOnHand(100)
                 .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();


        BeerDTO beer2 = BeerDTO.builder()
                .id(UUID.randomUUID())
                .version(1)
                 .beerName("Tusker")
                .beerStyle(BeerStyle.PALE_ALE)
                 .upc("032843480474")
                .price(new BigDecimal("9.99"))
                 .quantityOnHand(318)
                 .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        BeerDTO beer3 = BeerDTO.builder()
                .id(UUID.randomUUID())
                .version(1)
                 .beerName("Pilsner")
                .beerStyle(BeerStyle.PALE_ALE)
                 .upc("35676894322200")
                 .price(new BigDecimal("15.99"))
                 .quantityOnHand(240)
                 .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        beerMap.put(beer1.getId(),beer1);
        beerMap.put(beer2.getId(),beer2);
        beerMap.put(beer3.getId(),beer3);
    }

    @Override
    public List<BeerDTO> listBeers(){

        return new ArrayList<>(beerMap.values());
    }

     @Override
     public Optional<BeerDTO> getBeerById(UUID beerId) {
         log.debug("Get Beer By id in Service " + beerId.toString());
         return Optional.ofNullable(beerMap.get(beerId));
    }




    @Override
    public BeerDTO saveNewBeer(BeerDTO beer) {
        BeerDTO savedBeer = BeerDTO.builder()
                .id(UUID.randomUUID())
                .version(beer.getVersion())
                 .updateDate(LocalDateTime.now())
                 .createdDate(LocalDateTime.now())
                .beerName(beer.getBeerName())
                .beerStyle(beer.getBeerStyle())
                .quantityOnHand(beer.getQuantityOnHand())
                .price(beer.getPrice())
                 .upc(beer.getUpc())
                .build();
        beerMap.put(savedBeer.getId(),savedBeer);
        return savedBeer;
    }

    @Override
    public Optional<BeerDTO> updateBeerById(UUID beerId, BeerDTO beer) {
        BeerDTO existing = beerMap.get(beerId);
         existing.setBeerName(beer.getBeerName());
         existing.setPrice(beer.getPrice());
         existing.setUpc(beer.getUpc());
         existing.setQuantityOnHand(beer.getQuantityOnHand());
         beerMap.put(existing.getId(),existing);

        return Optional.of(existing);
    }
    
    @Override
    public void deleteBeerById(UUID beerId) {

        beerMap.remove(beerId);
    }

}
