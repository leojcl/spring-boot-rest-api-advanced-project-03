package com.leojcl.todos.controller;

import com.leojcl.todos.request.TodoRequest;
import com.leojcl.todos.response.TodoResponse;
import com.leojcl.todos.service.TodoService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<TodoResponse> getAllTodos(){
        return todoService.getAllTodos();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public TodoResponse createTodo(@Valid @RequestBody TodoRequest todoRequest){
        return todoService.createTodo(todoRequest);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    public TodoResponse toggleTodoCompletion(@PathVariable @Min(1) long id){
        return  todoService.toggleTodoCompletion(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    public void deleteTodo(@PathVariable @Min(1) long id){
        todoService.deleteTodo(id);
    }
}
