package com.mohan.class_com.controller;

import com.mohan.class_com.dto.CustomerRequestDto;
import com.mohan.class_com.dto.ResponseDto;
import com.mohan.class_com.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping
    public ResponseEntity<ResponseDto> createCustomer(@RequestBody CustomerRequestDto requestDto){
        return new ResponseEntity<>(customerService.registerCustomer(requestDto), HttpStatus.CREATED);

    }

}
