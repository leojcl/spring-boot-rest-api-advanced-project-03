package com.leojcl.todos.service;

import com.leojcl.todos.request.TodoRequest;
import com.leojcl.todos.response.TodoResponse;

import java.util.List;

public interface TodoService {
    TodoResponse createTodo(TodoRequest todoRequest);

    List<TodoResponse> getAllTodos();
}
