package com.mohan.class_com.service;

import com.mohan.class_com.dto.CartItemResponseDto;
import com.mohan.class_com.dto.ResponseDto;
import com.mohan.class_com.entity.*;
import com.mohan.class_com.exception.ResourceNotFoundEx;
import com.mohan.class_com.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepo;
    private final CartItemRepository cartItemRepo;
    private final CustomerRepository customerRepo;
    private final ProductRepository productRepo;
    private final UserRepository userRepo;

    public Customer getCustomer(){
        String email= SecurityContextHolder.getContext().getAuthentication().getName();

        User user= userRepo.findByEmail(email)
                .orElseThrow(()-> new ResourceNotFoundEx("User does not exists"));
        return customerRepo.findByUser_Id(user.getId())
                .orElseThrow(()-> new ResourceNotFoundEx("Customer does not exists"));


    }

    public CartItemResponseDto mapToDto(CartItem cartItem){
        CartItemResponseDto response=new CartItemResponseDto();
        response.setId(cartItem.getId());
        response.setTotalPrice(cartItem.getTotalPrice());
        response.setQuantity(cartItem.getQuantity());
        response.setProductName(cartItem.getProduct().getProductName());

        return response;

    }

    public void createCart(Customer customer){
        Cart cart=new Cart();
        cart.setCustomer(customer);

        cartRepo.save(cart);
    }

    public ResponseDto addProductToCart( Long productId, Integer quantity){

        Long customerId= getCustomer().getId();
        Product product= productRepo.findById(productId)
                .orElseThrow(()-> new  ResourceNotFoundEx("Product with id "+productId+" does not exists"));


        Cart cart=cartRepo.findByCustomer_Id(customerId)
                .orElseThrow(()-> new RuntimeException("Customer with id "+customerId+" does not have cart"));


       CartItem cartItem=cartItemRepo.findByCart_IdAndProduct_Id(cart.getId(),productId)
               .orElse(new CartItem(null, cart, product, product.getPrice()*quantity, quantity));

       if(cartItem.getId()!=null){
           cartItem.setQuantity(cartItem.getQuantity()+quantity);
           cartItem.setTotalPrice(cartItem.getQuantity()*product.getPrice());
       }




        CartItem savedCartItem=cartItemRepo.save(cartItem);

        return new ResponseDto("Product is successfully added to cart item with id "+savedCartItem.getId());


    }


    public List<CartItemResponseDto> getItemsInCart(){
        Long customerId=getCustomer().getId();

        Cart cart=cartRepo.findByCustomer_Id(customerId)
                .orElseThrow(()-> new ResourceNotFoundEx("Customer does not have cart"));

        List<CartItem> cartItems=cartItemRepo.findByCart_Id(cart.getId());

        return cartItems.stream()
                .map(this::mapToDto)
                .toList();

    }

    public void removeCartItem(Long cartItemId){
        Long customerId=getCustomer().getId();

        Cart cart=cartRepo.findByCustomer_Id(customerId)
                .orElseThrow(()-> new ResourceNotFoundEx("Customer does not have cart"));

        CartItem cartItem=cartItemRepo.findByIdAndCart_Id(cartItemId, cart.getId())
                        .orElseThrow(()-> new ResourceNotFoundEx("User does not cartItem"));

        cartItemRepo.delete(cartItem);

    }

    public void clearCart(){

        Long customerId=getCustomer().getId();
        Cart cart=cartRepo.findByCustomer_Id(customerId)
                .orElseThrow(()-> new ResourceNotFoundEx("Customer does not have cart"));

        List<CartItem> cartItems=cartItemRepo.findAllByCart_Id(cart.getId());

        cartItemRepo.deleteAll(cartItems);


    }
}
