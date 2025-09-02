package com.mohan.class_com.service;

import com.mohan.class_com.dto.CustomerRequestDto;
import com.mohan.class_com.dto.ResponseDto;
import com.mohan.class_com.entity.Customer;
import com.mohan.class_com.exception.ResourceAlreadyExistsEx;
import com.mohan.class_com.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepo;

    @Autowired
    private CartService cartService;

    public ResponseDto registerCustomer(CustomerRequestDto requestDto){
        Optional<Customer> existingCustomer=customerRepo.findByPhoneNumber(requestDto.getPhoneNumber());

        if(existingCustomer.isPresent()){
            throw new ResourceAlreadyExistsEx("Customer with mobile number "+requestDto.getPhoneNumber()+" already exists");
        }

        Customer customer=new Customer();
        customer.setName(requestDto.getName());
        customer.setAddress(requestDto.getAddress());
        customer.setPhoneNumber(requestDto.getPhoneNumber());

        Customer savedCustomer=customerRepo.save(customer);

        cartService.createCart(savedCustomer);

        return new ResponseDto("Customer successfully created with cart");

    }
}
