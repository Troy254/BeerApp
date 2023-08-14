package com.springframework.spring6restmvc.services;

import com.springframework.spring6restmvc.mapper.BeerMapper;
import com.springframework.spring6restmvc.model.BeerDTO;
import com.springframework.spring6restmvc.repositories.BeerRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Primary
@Service
@AllArgsConstructor
public class BeerServiceJPA implements BeerService {
    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;

    @Override
    public List<BeerDTO> listBeers() {
        return beerRepository.findAll()
                .stream()
                .map(beerMapper::beerToBeerDto)
                .collect(Collectors.toList());
    }

    @Override
    public BeerDTO getBeerById(UUID beerId) {
        return null;
    }

//    @Override
//    public BeerDTO getBeerById(UUID beerId) {
//        return (beerMapper.beerToBeerDto(beerRepository.findById(beerId)
//                .orElse(null)));
//    }

//    @Override
//    public  Optional<BeerDTO> getBeerById(UUID id){
//        return Optional.ofNullable(beerMapper.beerToBeerDto(beerRepository.findById(id)
//                .orElse(null)));
//    }


    @Override
    public BeerDTO saveNewBeer(BeerDTO beer) {
        return null;
    }

    @Override
    public void updateBeerById(UUID beerId, BeerDTO beer) {

    }

    @Override
    public void deleteBeerById(UUID beerId) {

    }
}
