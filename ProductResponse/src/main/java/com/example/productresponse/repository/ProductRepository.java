package com.example.productresponse.repository;

import com.tass.productservice.model.request.ProductRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductRequest, Long> {
    Optional<ProductRequest> findByBarcode(String barcode);
}
