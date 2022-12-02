package com.example.productresponse.services;

import com.example.productresponse.model.BaseResponse;
import com.example.productresponse.model.request.ProductRequest;
import com.example.productresponse.repository.ProductRepository;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Log4j2
public class ProductService {
    private static final Logger logger = LogManager.getLogger();
    @Autowired
    ProductRepository productRepository;
    public BaseResponse createdProduct(ProductRequest request){
        logger.info("start create");
        try{
            Optional<ProductRequest> productRequest = productRepository.findByBarcode(request.getBarcode());
            if (productRequest.isPresent()){
                logger.info("create fail");
                return new BaseResponse(1000, "Barcode exist!");
            } else if (request.getName() == null || request.getName().length() <= 0){
                logger.info("create fail");
                return new BaseResponse(100, "Product name can not be null!");
            } else if (request.getImage() == null || request.getImage().length() <= 0){
                logger.info("create fail");
                return new BaseResponse(101, "Product image can not be null!");
            } else if (request.getContent() == null || request.getContent().length() <= 0){
                logger.info("create fail");
                return new BaseResponse(101, "Product content can not be null!");
            } else if (request.getDescription() == null || request.getDescription().length() <= 0){
                logger.info("create fail");
                return new BaseResponse(101, "Product description can not be null!");
            } else {
                productRepository.save(request);
                logger.info("create success");
                return new BaseResponse(200, "Success");
            }
        }catch (DataAccessException | NullPointerException | ArrayIndexOutOfBoundsException | IllegalArgumentException | IllegalStateException | ArithmeticException e){
            logger.error(e);
            return new BaseResponse(500, "System is overloading, please try later!");
        }
    }
}
