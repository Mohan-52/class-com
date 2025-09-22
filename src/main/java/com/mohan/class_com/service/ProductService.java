package com.mohan.class_com.service;

import com.mohan.class_com.dto.ProductRequestDto;
import com.mohan.class_com.dto.ProductResponseDto;
import com.mohan.class_com.dto.ResponseDto;
import com.mohan.class_com.entity.Merchant;
import com.mohan.class_com.entity.Product;
import com.mohan.class_com.entity.User;
import com.mohan.class_com.exception.ResourceAlreadyExistsEx;
import com.mohan.class_com.exception.ResourceNotFoundEx;
import com.mohan.class_com.repository.MerchantRepository;
import com.mohan.class_com.repository.ProductRepository;
import com.mohan.class_com.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private MerchantRepository merchantRepo;

    public Merchant getMerchant(){
        String email= SecurityContextHolder.getContext().getAuthentication().getName();

        User user= userRepo.findByEmail(email)
                .orElseThrow(()-> new ResourceNotFoundEx("User does not exists"));

        return merchantRepo.findByUser_Id(user.getId())
                .orElseThrow(()-> new ResourceNotFoundEx("Merchant does not exists"));
    }

    private ProductResponseDto mapToDto(Product product){
        ProductResponseDto response= new ProductResponseDto();
        response.setProductId(product.getId());
        response.setStock(product.getStock());
        response.setProductName(product.getProductName());
        response.setDescription(product.getDescription());
        response.setCompanyName(product.getMerchant().getCompanyName());
        response.setPrice(product.getPrice());

        return response;
    }

    public ResponseDto createProduct(ProductRequestDto requestDto){

        Merchant merchant=getMerchant();

       Optional<Product> existingProduct=productRepo.findByProductName(requestDto.getName());

       if(existingProduct.isPresent()){
           throw new ResourceAlreadyExistsEx("Product with name "+requestDto.getName()+" already exists");
       }

       Product product=new Product();
       product.setProductName(requestDto.getName());
       product.setStock(requestDto.getStock());
       product.setPrice(requestDto.getPrice());
       product.setDescription(requestDto.getDescription());
       product.setMerchant(merchant);

       Product savedProduct=productRepo.save(product);

       return new ResponseDto("Product is successfully saved with product id "+savedProduct.getId());

    }


    public List<ProductResponseDto> getAllProducts(int page, int size){
        Sort sort= Sort.by(Sort.Direction.DESC, "stock");

        Pageable pageable= PageRequest.of(page,size, sort);


        Page<Product> productPage = productRepo.findAll(pageable);
        return productPage
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    public List<ProductResponseDto> getProductsOfMerchant(){
        Merchant merchant=getMerchant();

        return productRepo.findByMerchant_Id(merchant.getId())
                .stream()
                .map(this::mapToDto)
                .toList();
    }


    public ProductResponseDto getProductById(Long id){
        System.out.println("Getting from database");
        Product product=productRepo.findById(id)
                .orElseThrow(()-> new ResourceNotFoundEx("Product Not found"));

        return mapToDto(product);
    }


    public ProductResponseDto updateProduct(Long id, ProductRequestDto productRequestDto){
        Product existingProduct=productRepo.findById(id)
                .orElseThrow(()-> new ResourceNotFoundEx("Product with id "+id+ " does exists"));

        existingProduct.setProductName(productRequestDto.getName());
        existingProduct.setStock(productRequestDto.getStock());
        existingProduct.setDescription(productRequestDto.getDescription());
        existingProduct.setPrice(productRequestDto.getPrice());

       return mapToDto( productRepo.save(existingProduct));




    }
}
