package com.springframework.spring6restmvc.services;

import com.springframework.spring6restmvc.mapper.CustomerMapper;
import com.springframework.spring6restmvc.model.CustomerDTO;
import com.springframework.spring6restmvc.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Primary
@RequiredArgsConstructor
@Service
public class CustomerServiceJPA implements CustomerService {

  private CustomerRepository customerRepository;
  private CustomerMapper customerMapper;

  @Autowired
  public CustomerServiceJPA(CustomerRepository customerRepository, CustomerMapper customerMapper) {
    this.customerRepository = customerRepository;
    this.customerMapper = customerMapper;
  }

  @Override
  public List<CustomerDTO> listCustomers() {
    return customerRepository.findAll()
        .stream()
        .map(customerMapper::customerToCustomerDto)
        .collect(Collectors.toList());
  }

  @Override
  public Optional<CustomerDTO> getCustomerById(UUID id) {
    return Optional.ofNullable(
        customerMapper.customerToCustomerDto(customerRepository.findById(id).orElse(null)));
  }

  @Override
  public CustomerDTO saveNewCustomer(CustomerDTO customer) {
    return customerMapper.customerToCustomerDto(
        customerRepository.save(customerMapper.customerDtoCustomer(customer)));
  }

  @Override
  public Optional<CustomerDTO> updateCustomerById(UUID customerId, CustomerDTO customer) {
    AtomicReference<Optional<CustomerDTO>> atomicReference = new AtomicReference<>();
    customerRepository.findById(customerId).ifPresentOrElse(foundCustomer -> {
      foundCustomer.setFirstName(customer.getFirstName());
      foundCustomer.setLastName(customer.getLastName());
      foundCustomer.setPhoneNumber(customer.getPhoneNumber());
      atomicReference.set(Optional.of(
          customerMapper.customerToCustomerDto(customerRepository.save(foundCustomer))));

    }, () -> {
      atomicReference.set(Optional.empty());
    });
    return atomicReference.get();
  }

  @Override
  public void deleteCustomerById(UUID customerId) {
    customerRepository.deleteById(customerId);
  }
}
