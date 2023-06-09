package com.springframework.spring6restmvc.controllers;

import com.springframework.spring6restmvc.models.Customer;
import com.springframework.spring6restmvc.services.CustomerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("api/v1/customer")
@AllArgsConstructor
@Slf4j
@RestController
public class CustomerController {
    private CustomerService customerService;

    @DeleteMapping("customerId")
    public ResponseEntity deleteById(@PathVariable("customerId") UUID customerId){
        customerService.deleteCustomerById(customerId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PutMapping("{customerId}")
    public ResponseEntity updateById(@PathVariable("customerId") UUID customerId, @RequestBody Customer customer){
        customerService.upDateCustomerById(customerId,customer);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }


    @PostMapping
    public ResponseEntity postHandle(@RequestBody Customer customer){
        Customer savedCustomer = customerService.saveNewCustomer(customer);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "api/v1/customer" + savedCustomer.getId().toString());
        return new ResponseEntity(headers, HttpStatus.CREATED);
    }


    @RequestMapping(method = RequestMethod.GET)
    public List<Customer> customerList(){
        return customerService.customerList();
    }


    @RequestMapping("{customerId}")
    Customer getCustomerById(@PathVariable("customerId") UUID customerId){
        log.debug("Get Beer By Id - Controller");
        return customerService.getCustomerById(customerId);
    }
}
