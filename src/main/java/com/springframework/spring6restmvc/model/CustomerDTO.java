package com.springframework.spring6restmvc.model;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Builder
@Data
public class CustomerDTO {
    private UUID id;
    private Integer version;
    private String firstName;
    private String lastName;
    private String phoneNumber;
}