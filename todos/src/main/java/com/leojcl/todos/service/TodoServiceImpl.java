package com.leojcl.todos.service;

import com.leojcl.todos.entity.Todo;
import com.leojcl.todos.entity.User;
import com.leojcl.todos.repository.TodoRepository;
import com.leojcl.todos.request.TodoRequest;
import com.leojcl.todos.response.TodoResponse;
import com.leojcl.todos.util.FindAuthenticatedUser;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

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
    @Transactional(readOnly = true) // only get current user, help to optimal performance, data isolation
    public List<TodoResponse> getAllTodos() {

        User user = findAuthenticatedUser.getAuthenticatedUser();

        return todoRepository.findByOwner(user).stream()
                .map(this::convertTodoResponse).toList();
    }

    @Override
    @Transactional
    public TodoResponse toggleTodoCompletion(long id) {

        User user = findAuthenticatedUser.getAuthenticatedUser();

        Optional<Todo> todo = todoRepository.findByIdAndOwner(id, user);

        if(todo.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Todo not found");
        }
        todo.get().setComplete(!todo.get().isComplete());
        Todo updatedTodo = todoRepository.save(todo.get());

        return convertTodoResponse(updatedTodo);
    }

    @Override
    @Transactional
    public void deleteTodo(long id) {
        User user = findAuthenticatedUser.getAuthenticatedUser();
        Optional<Todo> todo = todoRepository.findByIdAndOwner(id, user);
        if(todo.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Todo not found");
        }
        todoRepository.delete(todo.get());
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
