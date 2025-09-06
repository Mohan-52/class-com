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

    @PostMapping("/product/{prodId}")
    public ResponseEntity<ResponseDto>  addProductToCart( @PathVariable Long prodId, @RequestParam Integer quantity){
        return new ResponseEntity<>(cartService.addProductToCart(prodId,quantity),HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CartItemResponseDto>> getMyProducts(){
        return ResponseEntity.ok(cartService.getItemsInCart());
    }

    @DeleteMapping("cartItem/{cartItemId}")
    public ResponseEntity<Void> removeCartItem(@PathVariable Long cstId, @PathVariable Long cartItemId){
        cartService.removeCartItem(cartItemId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/clear")
    public ResponseEntity<Void> clearCart(){
        cartService.clearCart();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
