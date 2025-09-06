package com.mohan.class_com.service;

import com.mohan.class_com.dto.MerchantRequestDto;
import com.mohan.class_com.dto.ResponseDto;
import com.mohan.class_com.entity.Merchant;
import com.mohan.class_com.entity.User;
import com.mohan.class_com.exception.ResourceAlreadyExistsEx;
import com.mohan.class_com.exception.ResourceNotFoundEx;
import com.mohan.class_com.repository.MerchantRepository;
import com.mohan.class_com.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MerchantService {
    @Autowired
    private MerchantRepository merchantRepo;

    @Autowired
    private UserRepository userRepo;

    public User getCurrentUser(){
        String email= SecurityContextHolder.getContext().getAuthentication().getName();

        return userRepo.findByEmail(email)
                .orElseThrow(()-> new ResourceNotFoundEx("User does not exists"));
    }

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
                .user(getCurrentUser())
        .build();

        Merchant savedMerchant= merchantRepo.save(merchant);

        return new ResponseDto("Merchant successfully Created With id "+savedMerchant.getId());

    }

    public List<Merchant> getAllMerchants(){
        return merchantRepo.findAll();
    }

    public Merchant getMerchantById(Long id){
        return merchantRepo.findById(id)
                .orElseThrow(()-> new ResourceNotFoundEx("Merchant with id "+id+" does not exists"));
    }

    public Merchant updateMerchant(Long id,MerchantRequestDto requestDto){
        Merchant existingMerchant= merchantRepo.findById(id)
                .orElseThrow(()->new  ResourceNotFoundEx("Merchant with id "+id+ " does not exists"));

        existingMerchant.setName(requestDto.getName());
        existingMerchant.setCompanyName(requestDto.getCompanyName());
        existingMerchant.setAddress(requestDto.getAddress());
        existingMerchant.setPhoneNumber(requestDto.getPhoneNumber());

        return merchantRepo.save(existingMerchant);
    }

    public void deleteMerchant(Long id){
        Merchant existingMerchant=merchantRepo.findById(id)
                .orElseThrow(()-> new ResourceNotFoundEx("Merchant with id "+id +" does not exosts"));

        merchantRepo.delete(existingMerchant);

    }
}
