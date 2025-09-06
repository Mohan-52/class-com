package com.mohan.class_com.controller;

import com.mohan.class_com.dto.ProductRequestDto;
import com.mohan.class_com.dto.ProductResponseDto;
import com.mohan.class_com.dto.ResponseDto;
import com.mohan.class_com.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PreAuthorize("hasRole('MERCHANT')")
    @PostMapping
    public ResponseEntity<ResponseDto> createProduct(@RequestBody ProductRequestDto requestDto){
        return new ResponseEntity<>(productService.createProduct(requestDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> getAllProducts(){
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @PreAuthorize("hasRole('MERCHANT')")
    @GetMapping("/my-products")
    public ResponseEntity<List<ProductResponseDto>> getAllProductOfMerchant(){
        return ResponseEntity.ok(productService.getProductsOfMerchant());
    }

    @PreAuthorize("hasRole('MERCHANT')")
    @PutMapping("/{prodId}")
    public ResponseEntity<ResponseDto> updateProduct(@PathVariable Long prodId, @RequestBody ProductRequestDto requestDto){
        return ResponseEntity.ok(productService.updateProduct(prodId,requestDto));
    }

}
