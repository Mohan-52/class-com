package com.mohan.class_com.controller;

import com.mohan.class_com.dto.MerchantRequestDto;
import com.mohan.class_com.dto.ResponseDto;
import com.mohan.class_com.entity.Merchant;
import com.mohan.class_com.service.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/merchant")
public class MerchantController {
    @Autowired
    private MerchantService merchantService;

    @PostMapping
    public ResponseEntity<ResponseDto> registerMerchant(@RequestBody MerchantRequestDto requestDto){
        return new ResponseEntity<>(merchantService.registerMerchant(requestDto), HttpStatus.CREATED) ;
    }

    @GetMapping
    public ResponseEntity<List<Merchant>> getAllMerchants(){
        return ResponseEntity.ok(merchantService.getAllMerchants());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Merchant> getMerchant(@PathVariable Long id){
        return ResponseEntity.ok(merchantService.getMerchantById(id));

    }

    @PutMapping("/{id}")
    public ResponseEntity<Merchant> updateMerchant(@PathVariable Long id, @RequestBody MerchantRequestDto requestDto){
        return ResponseEntity.ok(merchantService.updateMerchant(id, requestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMerchant(@PathVariable Long id){
        merchantService.deleteMerchant(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
