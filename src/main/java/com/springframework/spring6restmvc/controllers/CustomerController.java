package com.springframework.spring6restmvc.controllers;

import com.springframework.spring6restmvc.model.CustomerDTO;
import com.springframework.spring6restmvc.services.CustomerService;
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
@RequestMapping("/api/v1/customer")
public class CustomerController {
    CustomerService customerService;
    @DeleteMapping("{customerId}")
    public ResponseEntity deleteById(@PathVariable("customerId") UUID customerId){
        customerService.deleteCustomerById(customerId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PutMapping("{customerId}")
    public ResponseEntity updateById(@PathVariable("customerId") UUID customerId, @RequestBody CustomerDTO customer){
        customerService.updateCustomerById(customerId,customer);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping
    public ResponseEntity postHandle(@RequestBody CustomerDTO customer) {
        CustomerDTO savedCustomer = customerService.saveNewCustomer(customer);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/beer" + savedCustomer.getId().toString());
        return new ResponseEntity(headers,HttpStatus.CREATED);
    }



    @RequestMapping(method = RequestMethod.GET)
    public List<CustomerDTO> listCustomers(){return customerService.listCustomers();
    }


    @RequestMapping("{customerId}")
    public CustomerDTO getCustomerById(@PathVariable("customerId") UUID customerId){
        log.debug("Get Customer By Id - in Controller");
        return customerService.getCustomerById(customerId);

    }
}
