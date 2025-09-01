package com.mohan.class_com.service;

import com.mohan.class_com.dto.MerchantRequestDto;
import com.mohan.class_com.dto.ResponseDto;
import com.mohan.class_com.entity.Merchant;
import com.mohan.class_com.exception.ResourceAlreadyExistsEx;
import com.mohan.class_com.repository.MerchantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MerchantService {
    @Autowired
    private MerchantRepository merchantRepo;

    public ResponseDto registerMerchant(MerchantRequestDto requestDto){

        Optional<Merchant> existingMerchant=merchantRepo.findByCompanyName(requestDto.getCompanyName());

        if(existingMerchant.isPresent()){
            throw new ResourceAlreadyExistsEx("Company with name "+requestDto.getCompanyName()+" already exists");
        }

//        Merchant merchant= new Merchant();
//        merchant.setAddress(requestDto.getAddress());
//        merchant.setName(requestDto.getName());
//        merchant.setCompanyName(requestDto.getCompanyName());
//        merchant.setPhoneNumber(requestDto.getPhoneNumber());

        Merchant merchant= Merchant.builder()
                .name(requestDto.getName())
                .address(requestDto.getAddress())
                .phoneNumber(requestDto.getPhoneNumber())
                .companyName(requestDto.getCompanyName())
        .build();

        Merchant savedMerchant= merchantRepo.save(merchant);

        return new ResponseDto("Merchant successfully Created With id "+savedMerchant.getId());

    }
}
