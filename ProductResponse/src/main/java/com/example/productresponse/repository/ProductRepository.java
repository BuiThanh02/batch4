package com.example.productresponse.repository;

import com.example.productresponse.model.request.ProductRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductRequest, Long> {
    Optional<ProductRequest> findByBarcode(String barcode);
}
