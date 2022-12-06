package com.example.springprojecttasc.model.request;

import lombok.*;

import javax.persistence.*;

@Data
public class ProductRequest {
    private String barcode;
    private String name;
    private String image;
    private String description;
    private String content;
}
