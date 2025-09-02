package com.mohan.class_com.dto;

import lombok.Data;

@Data
public class ProductResponseDto {
    private Long productId;
    private String productName;
    private String description;
    private double price;
    private int stock;
    private String companyName;
}
