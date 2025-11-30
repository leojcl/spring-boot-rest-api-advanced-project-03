package com.leojcl.todos.service;

import com.leojcl.todos.entity.Todo;
import com.leojcl.todos.entity.User;
import com.leojcl.todos.repository.TodoRepository;
import com.leojcl.todos.request.TodoRequest;
import com.leojcl.todos.response.TodoResponse;
import com.leojcl.todos.util.FindAuthenticatedUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TodoServiceImpl implements TodoService{
    private  final TodoRepository todoRepository;
    private final FindAuthenticatedUser findAuthenticatedUser;

    public TodoServiceImpl(TodoRepository todoRepository, FindAuthenticatedUser findAuthenticatedUser) {
        this.todoRepository = todoRepository;
        this.findAuthenticatedUser = findAuthenticatedUser;
    }

    @Override
    @Transactional
    public TodoResponse createTodo(TodoRequest todoRequest) {
        User user = findAuthenticatedUser.getAuthenticatedUser();

        Todo todo = new Todo(todoRequest.getTitle(), todoRequest.getDescription(), todoRequest.getPriority(), false, user);

        Todo savedTodo = todoRepository.save(todo);


        return convertTodoResponse(savedTodo);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TodoResponse> getAllTodos() {

        User user = findAuthenticatedUser.getAuthenticatedUser();

        return todoRepository.findByOwner(user).stream()
                .map(this::convertTodoResponse).toList();
    }

    private TodoResponse convertTodoResponse(Todo todo){
        return new TodoResponse(
                todo.getId(),
                todo.getTitle(),
                todo.getDescription(),
                todo.getPriority(),
                todo.isComplete()
        );
    }

}
