package com.alexj03.todo.controller;

import com.alexj03.todo.dto.TaskDto;
import com.alexj03.todo.model.Category;
import com.alexj03.todo.model.Priority;
import com.alexj03.todo.model.Status;
import com.alexj03.todo.model.Task;
import com.alexj03.todo.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tasks")
@Tag(name = "Task Controller", description = "Task Management Controller")
public class TaskController {

    private final TaskService taskService;

    @GetMapping
    @Operation(summary = "Finds all tasks", description = "Finds all tasks and returns all tasks")
    public ResponseEntity<List<Task>> findAll(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Status status,
            @RequestParam(required = false) Priority priority,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate deadline,
            @RequestParam(required = false) Category category,
            @RequestParam(defaultValue = "deadline") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ) {
        return ResponseEntity.ok(taskService.findFilteredTasks(title, status, priority, deadline, category, sortBy, sortDir));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Finds a task by id", description = "Finds a task by id and returns the data of a single task by its ID")
    public ResponseEntity<Task> findById(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.findById(id));
    }

    @GetMapping("/today")
    @Operation(summary = "Finds today tasks", description = "Finds today tasks and returns tasks with deadline = today")
    public ResponseEntity<List<Task>> findTodayTasks() {
        return ResponseEntity.ok(taskService.findTodayTasks());
    }

    @GetMapping("/tomorrow")
    @Operation(summary = "Finds tomorrow tasks", description = "Finds tomorrow tasks and returns tasks with deadline = tomorrow")
    public ResponseEntity<List<Task>> findTomorrowTasks() {
        return ResponseEntity.ok(taskService.findTomorrowTasks());
    }

    @GetMapping("/week")
    @Operation(summary = "Finds weekly tasks", description = "Finds weekly tasks and returns tasks with deadline = current week (starting from monday)")
    public ResponseEntity<List<Task>> findWeekTasks() {
        return ResponseEntity.ok(taskService.findWeekTasks());
    }

    @GetMapping("/important")
    @Operation(summary = "Finds important tasks", description = "Finds important tasks and returns tasks with priority = HIGH")
    public ResponseEntity<List<Task>> findImportantTasks() {
        return ResponseEntity.ok(taskService.findImportantTasks());
    }

    @PostMapping
    @Operation(summary = "Creates a task", description = "Creates a task and returns the created task")
    public ResponseEntity<Task> create(@RequestBody TaskDto taskDto) {
        return ResponseEntity.ok(taskService.create(taskDto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletes a task", description = "Deletes the task and returns nothing")
    public void delete(@PathVariable Long id) {
        taskService.delete(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Updates a task", description = "Updates the task and returns the updated task")
    public ResponseEntity<Task> update(@PathVariable Long id, @RequestBody TaskDto taskDto) {
        return ResponseEntity.ok(taskService.update(id, taskDto));
    }
}
