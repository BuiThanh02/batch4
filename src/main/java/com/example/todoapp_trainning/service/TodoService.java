package com.example.todoapp_trainning.service;

import com.example.todoapp_trainning.entity.Status;
import com.example.todoapp_trainning.entity.Todo;
import com.example.todoapp_trainning.repository.TodoRepository;
import com.example.todoapp_trainning.search.SearchBody;
import com.example.todoapp_trainning.search.SearchCriteria;
import com.example.todoapp_trainning.search.TodoSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.example.todoapp_trainning.search.SearchCriteriaOperator.*;

@Service
@Transactional
@RequiredArgsConstructor
public class TodoService {
    @Autowired
    TodoRepository todoRepository;

    public Map<String, Object> findAll(SearchBody searchBody){
        Specification specification = Specification.where(null);

        if (searchBody.getStatus() > -1){
            specification = specification.and(new TodoSpecification(new SearchCriteria("status", EQUALS, searchBody.getStatus())));
        }
        if (searchBody.getName() != null && searchBody.getName().length() > 0 ){
            specification = specification.and(new TodoSpecification(new SearchCriteria("name", LIKE, "%" + searchBody.getName() + "%")));
        }

        Sort sort = Sort.by(Sort.Order.asc("expiredAt"));
        if (searchBody.getSort() !=null && searchBody.getSort().length() >0){
            if (searchBody.getSort().contains("desc")){
                sort = Sort.by(Sort.Order.desc("expiredAt"));
            }
        }

        Pageable pageable = PageRequest.of(searchBody.getPage() -1, searchBody.getLimit(),sort );
        Page<Todo> todoPage = todoRepository.findAll(specification,pageable);
        List<Todo> todoList = todoPage.getContent();
        Map<String, Object> responses = new HashMap<>();
        responses.put("content",todoList);
        responses.put("currentPage",todoPage.getNumber() + 1);
        responses.put("totalItems",todoPage.getTotalElements());
        responses.put("totalPage",todoPage.getTotalPages());
        return responses;
    }

    public int countByStatus(Status status){
        return todoRepository.findAllByStatus(status).size();
    }

    public Todo save(Todo todo){
        return todoRepository.save(todo);
    }

    public Optional<Todo> findById(Long id){
        return todoRepository.findById(id);
    }
}
