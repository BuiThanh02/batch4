package com.example.springprojecttasc.database.repository;

import com.example.springprojecttasc.database.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>,CategoryCustomRepository {
    List<Category> findAllByNameContains(String name);

    @Query(value = "select * from category c, category_relationship cr where c.id = cr.parent_id",nativeQuery = true)
    List<Category> findAllChildren(Long id);

    @Query(value = "select * from category c, category_relationship cr where c.id = cr.category_id",nativeQuery = true)
    List<Category> findAllParent(Long id);
}
