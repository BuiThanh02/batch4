package com.example.todoapp_trainning.api;

import com.example.todoapp_trainning.entity.Status;
import com.example.todoapp_trainning.entity.Todo;
import com.example.todoapp_trainning.search.SearchBody;
import com.example.todoapp_trainning.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/todo")
@RequiredArgsConstructor
@CrossOrigin("*")
public class TodoApi {
    @Autowired
    TodoService todoService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> findAll(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "limit", defaultValue = "10") int limit,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "sort", required = false) String sort,
            @RequestParam(name = "status", defaultValue = "-1") int status
    ){
        SearchBody searchBody = SearchBody.SearchBodyBuilder.aSearchBody()
                .withPage(page)
                .withLimit(limit)
                .withName(name)
                .withSort(sort)
                .withStatus(status)
                .build();
        return ResponseEntity.ok(todoService.findAll(searchBody));
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    public ResponseEntity<Todo> findById(@PathVariable Long id){
        Optional<Todo> optionalTodo = todoService.findById(id);
        if (!optionalTodo.isPresent()){
            ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(optionalTodo.get());
    }

    @RequestMapping(method = RequestMethod.GET, path = "/count/{status}")
    public int countByStatus(@PathVariable int status){
        Status status1 = Status.TODO;
        if (status == 0 ){
            status1 = Status.DONE;
        }
        if (status == 1 ){
            status1 = Status.IN_PROGRESS;
        }
        if (status == 2 ){
            status1 = Status.CANCELLED;
        }
        return todoService.countByStatus(status1);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> create(@RequestBody Todo todo){
        todo.setCreatedAt(LocalDateTime.now());
        todo.setUpdatedAt(LocalDateTime.now());
        todo.setStatus(Status.TODO);
        return ResponseEntity.ok(todoService.save(todo));
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Todo updateTodo){
        Optional<Todo> todo = todoService.findById(id);
        if (!todo.isPresent()){
            return ResponseEntity.badRequest().body("Not exist!");
        }
        todo.get().setUpdatedAt(LocalDateTime.now());
        todo.get().setName(updateTodo.getName());
        todo.get().setExpiredAt(updateTodo.getExpiredAt());
        todo.get().setStepList(updateTodo.getStepList());
        return ResponseEntity.ok(todoService.save(todo.get()));
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/{id}/{status}")
    public ResponseEntity<?> updateStatus(@PathVariable Long id, @PathVariable int status){
        Optional<Todo> todo = todoService.findById(id);
        if (!todo.isPresent()){
            return ResponseEntity.badRequest().body("Not exist!");
        }
        Status status1 = Status.TODO;
        if (status == 0 ){
            status1 = Status.DONE;
        }
        if (status == 1 ){
            status1 = Status.IN_PROGRESS;
        }
        if (status == 2 ){
            status1 = Status.CANCELLED;
        }
        todo.get().setStatus(status1);
        return ResponseEntity.ok(todoService.save(todo.get()));
    }
}
