package com.example.todoapp_trainning.seeder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ApplicationSeeder implements CommandLineRunner {
    @Autowired
    TodoSeeder todoSeeder;
    @Override
    public void run(String... args) throws Exception {
        todoSeeder.generate();
    }
}
