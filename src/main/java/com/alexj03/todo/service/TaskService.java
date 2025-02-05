package com.alexj03.todo.service;

import com.alexj03.todo.dto.TaskDto;
import com.alexj03.todo.exception.TaskNotFoundException;
import com.alexj03.todo.model.Task;
import com.alexj03.todo.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public List<Task> getAll() {
        log.info("Получение списка задач...");
        return taskRepository.findAll();
    }

    public Task getById(Long id) throws TaskNotFoundException {
        log.info("Получение задачи под идентификатором {}...", id);
        return taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Задача с идентификатором " + id + " не найдена!"));
    }

    public Task create(TaskDto taskDto) {
        log.info("Создание задачи с названием {}...", taskDto.getTitle());
        Task task = Task.builder()
                .createdAt(LocalDateTime.now())
                .title(taskDto.getTitle())
                .description(taskDto.getDescription())
                .status(taskDto.getStatus())
                .priority(taskDto.getPriority())
                .deadline(taskDto.getDeadline())
                .build();

        taskRepository.save(task);
        return task;
    }

    public void delete(Long id) {
        log.info("Удаление задачи под идентификатором {}...", id);
        taskRepository.deleteById(id);
    }

    public Task update(Long id, TaskDto taskDto) {
        log.info("Изменение задачи под идентификатором {}...", id);
        Task task = Task.builder()
                .id(id)
                .title(taskDto.getTitle())
                .description(taskDto.getDescription())
                .priority(taskDto.getPriority())
                .deadline(taskDto.getDeadline())
                .status(taskDto.getStatus())
                .build();
        taskRepository.save(task);
        return task;
    }

    public List<Task> getByTitle(String title) throws TaskNotFoundException {
        log.info("Получение задачи по названию {}...", title);
        return taskRepository.findByTitle(title)
                .orElseThrow(() -> new TaskNotFoundException("Задачи с заголовком " + title + " не существует!"));
    }
}
