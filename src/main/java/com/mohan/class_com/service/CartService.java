package com.mohan.class_com.service;

import com.mohan.class_com.entity.Cart;
import com.mohan.class_com.entity.Customer;
import com.mohan.class_com.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepo;

    public void createCart(Customer customer){
        Cart cart=new Cart();
        cart.setCustomer(customer);

        cartRepo.save(cart);

    }
}
