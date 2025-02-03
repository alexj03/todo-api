package com.alexj03.todo.controller;

import com.alexj03.todo.dto.TodoDto;
import com.alexj03.todo.exception.TaskNotFoundException;
import com.alexj03.todo.model.Todo;
import com.alexj03.todo.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/todo")
public class TodoController {

    private final TodoService todoService;

    @GetMapping("/")
    public List<Todo> getTodos(@RequestParam(required = false) String title) throws TaskNotFoundException {
        if (title != null) {
            return todoService.getByTitle(title);
        }

        return todoService.getAll();
    }

    @GetMapping("/{id}")
    public Todo getTodoById(@PathVariable Long id) throws TaskNotFoundException {
        return todoService.getById(id);
    }

    @PostMapping("/")
    public Todo createTodo(@RequestBody TodoDto todoDto) {
        return todoService.create(todoDto);
    }

    @DeleteMapping("/{id}")
    public List<Todo> deleteTodo(@PathVariable Long id) {
        return todoService.delete(id);
    }

    @PutMapping("/")
    public List<Todo> updateTodo(@RequestBody Todo todo) {
        return todoService.update(todo);
    }
}
