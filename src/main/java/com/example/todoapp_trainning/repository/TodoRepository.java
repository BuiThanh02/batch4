package com.example.todoapp_trainning.repository;


import com.example.todoapp_trainning.entity.Status;
import com.example.todoapp_trainning.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long>, JpaSpecificationExecutor<Todo> {

    List<Todo> findAllByStatus(Status status);
}
