package com.mohan.class_com.service;

import com.mohan.class_com.dto.ProductRequestDto;
import com.mohan.class_com.dto.ProductResponseDto;
import com.mohan.class_com.dto.ResponseDto;
import com.mohan.class_com.entity.Merchant;
import com.mohan.class_com.entity.Product;
import com.mohan.class_com.exception.ResourceAlreadyExistsEx;
import com.mohan.class_com.exception.ResourceNotFoundEx;
import com.mohan.class_com.repository.MerchantRepository;
import com.mohan.class_com.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepo;

    @Autowired
    private MerchantRepository merchantRepo;

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

    public ResponseDto createProduct(Long id,ProductRequestDto requestDto){

        Merchant merchant=merchantRepo.findById(id)
                .orElseThrow(()-> new ResourceNotFoundEx("Merchant with id "+id + " does not exits"));

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

    public List<ProductResponseDto> getAllProducts(){
        return productRepo.findAll()
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    public List<ProductResponseDto> getProductsOfMerchant(Long id){
        Merchant merchant=merchantRepo.findById(id)
                .orElseThrow(()-> new ResourceNotFoundEx("Merchant with id "+id +" does not exits"));

        return productRepo.findByMerchant_Id(id)
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    public ResponseDto updateProduct(Long id, ProductRequestDto productRequestDto){
        Product existingProduct=productRepo.findById(id)
                .orElseThrow(()-> new ResourceNotFoundEx("Product with id "+id+ " does exists"));

        existingProduct.setProductName(productRequestDto.getName());
        existingProduct.setStock(productRequestDto.getStock());
        existingProduct.setDescription(productRequestDto.getDescription());
        existingProduct.setPrice(productRequestDto.getPrice());

        productRepo.save(existingProduct);

        return new ResponseDto("Product successfully updated");


    }
}
