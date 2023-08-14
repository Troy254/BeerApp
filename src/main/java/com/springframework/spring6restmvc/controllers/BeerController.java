package com.springframework.spring6restmvc.controllers;

 import com.springframework.spring6restmvc.model.BeerDTO;
import com.springframework.spring6restmvc.services.BeerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
   @AllArgsConstructor
   @RequestMapping("/api/v1/beer")
public class BeerController {
//     public static final String BEER_PATH = "/api/v1/beer";
//     public static final String BEER_PATH_ID = BEER_PATH + "{beerId}";
    private BeerService beerService;

    @DeleteMapping("{beerId}")
    public ResponseEntity deleteById(@PathVariable("beerId") UUID beerId){
        beerService.deleteBeerById(beerId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PutMapping("{beerId}")
       public ResponseEntity updateById(@PathVariable("beerId") UUID beerId,@RequestBody BeerDTO beer){
           beerService.updateBeerById(beerId,beer);
           return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping
    public ResponseEntity postHandle(@RequestBody BeerDTO beer){
        BeerDTO savedBeer = beerService.saveNewBeer(beer);
        HttpHeaders headers = new HttpHeaders();
           headers.add("Location", "/api/v1/beer" + savedBeer.getId().toString());
        return new ResponseEntity(headers,HttpStatus.CREATED);
    }

     @RequestMapping(method = RequestMethod.GET)
     public List<BeerDTO> listBeers() {

        return beerService.listBeers();
    }


     @RequestMapping("{beerId}")
     public BeerDTO getBeerById(@PathVariable("beerId") UUID beerId){

        log.debug("Get Beer By Id - in Controller");

        return beerService.getBeerById(beerId);
    }
}
