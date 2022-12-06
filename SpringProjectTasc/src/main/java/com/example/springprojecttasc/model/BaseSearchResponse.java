package com.example.springprojecttasc.model;

import lombok.Data;

@Data
public class BaseSearchResponse {
    private int currentPage;
    private int pageSize;
    private long totalItem;
}
