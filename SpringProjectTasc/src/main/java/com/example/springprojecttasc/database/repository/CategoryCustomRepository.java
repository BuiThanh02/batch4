package com.example.springprojecttasc.database.repository;

import com.example.springprojecttasc.database.entity.Category;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryCustomRepository {
    List<Category> findAll(String query);

    List<Category> findAllWithPage(String query, int page, int pageSize);
}
