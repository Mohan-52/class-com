package com.mohan.class_com.controller;

import com.mohan.class_com.dto.ProductRequestDto;
import com.mohan.class_com.dto.ProductResponseDto;
import com.mohan.class_com.dto.ResponseDto;
import com.mohan.class_com.entity.Product;
import com.mohan.class_com.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/merchant/{id}")
    public ResponseEntity<ResponseDto> createProduct(@PathVariable Long id, @RequestBody ProductRequestDto requestDto){
        return new ResponseEntity<>(productService.createProduct(id,requestDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> getAllProducts(){
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/merchant/{id}")
    public ResponseEntity<List<ProductResponseDto>> getAllProductOfMerchant(@PathVariable Long id){
        return ResponseEntity.ok(productService.getProductsOfMerchant(id));
    }

    @PutMapping("/{prodId}")
    public ResponseEntity<ResponseDto> updateProduct(@PathVariable Long prodId, @RequestBody ProductRequestDto requestDto){
        return ResponseEntity.ok(productService.updateProduct(prodId,requestDto));
    }

}
