package com.alexj03.todo.controller;

import com.alexj03.todo.dto.TodoDto;
import com.alexj03.todo.model.Todo;
import com.alexj03.todo.exception.TaskNotFoundException;
import com.alexj03.todo.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/todo")
public class TodoController {

    private final TodoService todoService;

    @GetMapping("/")
    public List<Todo> getAllTodos() {
        return todoService.getAll();
    }

    @GetMapping("/{id}")
    public Todo getTodoById(@PathVariable Long id) throws TaskNotFoundException {
        return todoService.getById(id);
    }

    @PostMapping("/")
    public void createTodo(@RequestBody TodoDto todoDto) {
        todoService.create(todoDto);
    }

    @DeleteMapping("/{id}")
    public void deleteTodo(@PathVariable Long id) {
        todoService.delete(id);
    }

    @PutMapping("/")
    public void updateTodo(@RequestBody Todo todo) {
        todoService.update(todo);
    }
}
