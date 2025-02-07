package com.alexj03.todo.controller;

import com.alexj03.todo.dto.TaskDto;
import com.alexj03.todo.exception.TaskNotFoundException;
import com.alexj03.todo.model.Task;
import com.alexj03.todo.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/todo")
public class TaskController {

    private final TaskService taskService;

    @GetMapping("/")
    public ResponseEntity<List<Task>> getAll(@RequestParam(required = false) String title) throws TaskNotFoundException {
        if (title != null) {
            return ResponseEntity.ok(taskService.getByTitle(title));
        }

        return ResponseEntity.ok(taskService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getById(@PathVariable Long id) throws TaskNotFoundException {
        return ResponseEntity.ok(taskService.getById(id));
    }

    @PostMapping("/")
    public ResponseEntity<TaskDto> create(@RequestBody TaskDto taskDto) {
        return ResponseEntity.ok(taskService.create(taskDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        taskService.delete(id);
        return ResponseEntity.ok("Задача удалена");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> update(@PathVariable Long id, @RequestBody TaskDto taskDto) {
        return ResponseEntity.ok(taskService.update(id, taskDto));
    }

    @GetMapping("/today")
    public ResponseEntity<List<Task>> getTodayTasks() {
        return ResponseEntity.ok(taskService.getTodayTasks());
    }

    @GetMapping("/tomorrow")
    public ResponseEntity<List<Task>> getTomorrowTasks() {
        return ResponseEntity.ok(taskService.getTomorrowTasks());
    }

    @GetMapping("/week")
    public ResponseEntity<List<Task>> getWeekTasks() {
        return ResponseEntity.ok(taskService.getWeekTasks());
    }

    @GetMapping("/important")
    public ResponseEntity<List<Task>> getImportantTasks() {
        return ResponseEntity.ok(taskService.getImportantTasks());
    }
}
