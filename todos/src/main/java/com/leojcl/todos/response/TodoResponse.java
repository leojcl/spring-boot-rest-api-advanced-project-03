package com.leojcl.todos.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TodoResponse {
    private long id;
    private String title;
    private String description;
    private int priority;
    private boolean complete;

    public TodoResponse(long id, String title, String description, int priority, boolean complete) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.complete = complete;
    }
}
