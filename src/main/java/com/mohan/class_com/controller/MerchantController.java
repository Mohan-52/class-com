package com.mohan.class_com.controller;

import com.mohan.class_com.dto.MerchantRequestDto;
import com.mohan.class_com.dto.ResponseDto;
import com.mohan.class_com.service.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/merchant")
public class MerchantController {
    @Autowired
    private MerchantService merchantService;

    @PostMapping
    public ResponseEntity<ResponseDto> registerMerchant(@RequestBody MerchantRequestDto requestDto){
        return new ResponseEntity<>(merchantService.registerMerchant(requestDto), HttpStatus.CREATED) ;
    }
}
