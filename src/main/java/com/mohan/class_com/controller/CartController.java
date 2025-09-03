package com.mohan.class_com.controller;

import com.mohan.class_com.dto.CartItemResponseDto;
import com.mohan.class_com.dto.ResponseDto;
import com.mohan.class_com.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @PostMapping("/customer/{cstId}/product/{prodId}")
    public ResponseEntity<ResponseDto>  addProductToCart(@PathVariable Long cstId, @PathVariable Long prodId, @RequestParam Integer quantity){
        return new ResponseEntity<>(cartService.addProductToCart(cstId,prodId,quantity),HttpStatus.CREATED);
    }

    @GetMapping("/customer/{id}")
    public ResponseEntity<List<CartItemResponseDto>> getMyProducts(@PathVariable Long id){
        return ResponseEntity.ok(cartService.getItemsInCart(id));
    }

    @DeleteMapping("/customer/{cstId}/cartItem/{cartItemId}")
    public ResponseEntity<Void> removeCartItem(@PathVariable Long cstId, @PathVariable Long cartItemId){
        cartService.removeCartItem(cstId,cartItemId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/clear/customer/{id}")
    public ResponseEntity<Void> clearCart(@PathVariable Long id){
        cartService.clearCart(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
