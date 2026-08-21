package com.poc.pi.domain;

import com.poc.pi.domain.types.Task;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Document
public class User {

    @Id
    private UUID id;


    private String username;
    private String password;

    private final List<Task> tasks = new ArrayList<>();

    public List<Task> getTasks() {
        return tasks;
    }

    public UUID getId() {
        return id;
    }



    public String getUsername() {
        return username;
    }



    public String getPassword() {
        return password;
    }

    public User(){}
}
