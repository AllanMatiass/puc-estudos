package com.poc.pi.domain.types;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

public record Task(
        String title,
        String description,
        boolean complete,
        List<Task> subTasks
) {
    public void add(Task t){
        if (cannotAdd()){
            throw new ResponseStatusException(HttpStatus.CONTENT_TOO_LARGE, "Num pode");
        }

        this.subTasks.add(t);
    }

    private boolean cannotAdd(){
        return this.subTasks.size() > 5;
    }

    public Task(Task t){
        this(t.title, t.description, t.complete, t.subTasks);
    }

    public Task(String title, String description, boolean complete){
        this(title, description, complete, new ArrayList<>());
    }


}
