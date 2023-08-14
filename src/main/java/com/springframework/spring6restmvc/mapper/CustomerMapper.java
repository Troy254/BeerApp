package com.springframework.spring6restmvc.mapper;


import com.springframework.spring6restmvc.entities.Customer;
import com.springframework.spring6restmvc.model.CustomerDTO;
import org.mapstruct.Mapper;

@Mapper
public interface CustomerMapper {

     //This method is responsible for mapping a CustomerDTO object to a Customer object.
    // It takes a CustomerDTO as input and returns a Customer.
    Customer customerDtoCustomer(CustomerDTO dto);

     //This method does the reverse mapping, converting a Customer object to a CustomerDTO object.
    // It takes a Customer as input and returns a CustomerDTO
    CustomerDTO customerToCustomerDto(Customer customer);

}
