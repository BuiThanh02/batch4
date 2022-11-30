package com.example.todoapp_trainning.seeder;

import com.example.todoapp_trainning.entity.Status;
import com.example.todoapp_trainning.entity.Step;
import com.example.todoapp_trainning.entity.Todo;
import com.example.todoapp_trainning.repository.StepRepository;
import com.example.todoapp_trainning.repository.TodoRepository;
import com.example.todoapp_trainning.util.RandomLocalDateTime;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TodoSeeder {
    @Autowired
    TodoRepository todoRepository;

    @Autowired
    StepRepository stepRepository;

    Faker faker = new Faker();

    public static final int TODOLIST_SIZE = 40;

    public static List<Todo> todoList = new ArrayList<>();
    public void generate(){
        for (int i = 0; i < TODOLIST_SIZE; i++) {
            Todo todo = new Todo();
            int randomStatus = faker.number().numberBetween(0, 4);
            if (randomStatus == 0){
                todo.setStatus(Status.DONE);
            }else if (randomStatus == 1){
                todo.setStatus(Status.IN_PROGRESS);
            }else if (randomStatus == 2){
                todo.setStatus(Status.CANCELLED);
            }else if (randomStatus == 3){
                todo.setStatus(Status.TODO);
            }
            int randomStep = faker.number().numberBetween(1, 4);
            List<Step> stepList = new ArrayList<>();
            for (int j = 0; j < randomStep; j++) {
                Step step = new Step();
                step.setName(faker.lorem().sentence());
                stepList.add(step);
            }
            stepRepository.saveAll(stepList);
            todo.setStepList(stepList);
            todo.setName(faker.lorem().sentence());
            todo.setCreatedAt(RandomLocalDateTime.generateLocalDate());
            todo.setUpdatedAt(RandomLocalDateTime.generateLocalDate());
            todo.setExpiredAt(RandomLocalDateTime.generateLocalDate());
            todoList.add(todo);
        }
        todoRepository.saveAll(todoList);
    }
}
