package com.example.springprojecttasc.services;

import com.example.springprojecttasc.database.entity.Product;
import com.example.springprojecttasc.database.repository.ProductRepository;
import com.example.springprojecttasc.model.BaseResponse;
import com.example.springprojecttasc.model.request.ProductRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.logging.LogManager;
import java.util.logging.Logger;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;
    public BaseResponse createdProduct(ProductRequest request){
        try{
            Optional<Product> productRequest = productRepository.findByBarcode(request.getBarcode());
            if (productRequest.isPresent()){
                return new BaseResponse(1000, "Barcode exist!");
            } else if (request.getName() == null || request.getName().length() <= 0){
                return new BaseResponse(100, "Product name can not be null!");
            } else if (request.getImage() == null || request.getImage().length() <= 0){
                return new BaseResponse(101, "Product image can not be null!");
            } else if (request.getContent() == null || request.getContent().length() <= 0){
                return new BaseResponse(101, "Product content can not be null!");
            } else if (request.getDescription() == null || request.getDescription().length() <= 0){
                return new BaseResponse(101, "Product description can not be null!");
            } else {
                Product product = new Product();
                product.setBarcode(request.getBarcode());
                product.setContent(request.getContent());
                product.setDescription(request.getDescription());
                product.setImage(request.getImage());
                product.setName(request.getName());
                productRepository.save(product);
                return new BaseResponse(200, "Success");
            }
        }catch (DataAccessException | NullPointerException | ArrayIndexOutOfBoundsException | IllegalArgumentException | IllegalStateException | ArithmeticException e){
            return new BaseResponse(500, "System is overloading, please try later!");
        }
    }
}
