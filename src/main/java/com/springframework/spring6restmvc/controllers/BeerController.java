package com.springframework.spring6restmvc.controllers;

import com.springframework.spring6restmvc.models.Beer;
import com.springframework.spring6restmvc.services.BeerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.UUID;
@RequestMapping("api/v1/beer")
@Slf4j
@AllArgsConstructor
@RestController
public class BeerController {
    private BeerService beerService;

    @DeleteMapping("{beerId}")
    public ResponseEntity deleteById(@PathVariable("beerId") UUID beerId){
        beerService.deleteBeerById(beerId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PutMapping("{beerId}")
    public ResponseEntity updateById(@RequestBody Beer beer, @PathVariable("beerId") UUID beerId){
       beerService.updateBeerById(beer,beerId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }


    @PostMapping
   //@RequestMapping(method = RequestMethod.POST)
   public ResponseEntity postHandle(@RequestBody Beer beer){
       Beer savedBeer = beerService.saveNewBeer(beer);
       HttpHeaders headers = new HttpHeaders();
       headers.add("Location","api/v1/beer" + savedBeer.getId().toString());
       return new ResponseEntity(headers,HttpStatus.CREATED);
   }

    @RequestMapping(method = RequestMethod.GET)
    public List<Beer> listBeers(){
        return beerService.listBeers();
    }


    @RequestMapping("{beerId}")
    public Beer getBeerById(@PathVariable("beerId") UUID beerId){
        log.debug("Get Beer By Id - Controllers");
        return beerService.getBeerById(beerId);
    }

}
