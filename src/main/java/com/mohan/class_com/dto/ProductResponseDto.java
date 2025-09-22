package com.mohan.class_com.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ProductResponseDto implements Serializable {
    private Long productId;
    private String productName;
    private String description;
    private double price;
    private int stock;
    private String companyName;
}
