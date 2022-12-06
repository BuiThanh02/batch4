package com.example.springprojecttasc.database.repository.impl;

import com.example.springprojecttasc.database.entity.Category;
import com.example.springprojecttasc.database.repository.CategoryCustomRepository;
import com.example.springprojecttasc.model.request.CategoryRequest;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.transform.ResultTransformer;

import javax.persistence.PersistenceContext;
import java.util.List;

public class CategoryCustomRepositoryImpl implements CategoryCustomRepository {
    @PersistenceContext
    Session session;

    @Override
    public List<Category> findAll(String query) {
        return session.createNativeQuery(query, Category.class).getResultList();
    }

    @Override
    public List<Category> findAllWithPage(String query, int page, int pageSize){
        return session.createNativeQuery(query, Category.class).setMaxResults(page).setFirstResult(page*pageSize).getResultList();
    }

}
