package com.alexj03.todo.service;

import com.alexj03.todo.dto.TaskDto;
import com.alexj03.todo.exception.TaskNotFoundException;
import com.alexj03.todo.model.Category;
import com.alexj03.todo.model.Priority;
import com.alexj03.todo.model.Task;
import com.alexj03.todo.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final CategoryService categoryService;

    public List<Task> getAll() {
        log.info("Получение списка задач...");
        return taskRepository.findAll();
    }

    public Task getById(Long id) throws TaskNotFoundException {
        log.info("Получение задачи под идентификатором {}...", id);
        return taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Задача с идентификатором " + id + " не найдена!"));
    }

    public TaskDto create(TaskDto taskDto) {
        log.info("Создание задачи с названием {} и категорией {}...", taskDto.getTitle(), taskDto.getCategoryId());

        Category category = categoryService.getById(taskDto.getCategoryId());

        Task task = Task.builder()
                .createdAt(LocalDateTime.now())
                .title(taskDto.getTitle())
                .description(taskDto.getDescription())
                .status(taskDto.getStatus())
                .priority(taskDto.getPriority())
                .deadline(taskDto.getDeadline() != null ? taskDto.getDeadline().plusDays(1) : null)
                .createdAt(LocalDateTime.now())
                .build();

        task.setCategory(category);

        taskRepository.save(task);
        return taskDto;
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

    public List<Task> getTodayTasks() {
        log.info("Получение задач со сроком на сегодня...");
        return taskRepository.findAllByDeadline(LocalDate.now()).orElseThrow(() -> new IllegalArgumentException("Данные не верны"));
    }

    public List<Task> getTomorrowTasks() {
        log.info("Получение задач со сроком на завтра...");
        return taskRepository.findAllByDeadline(LocalDate.now().plusDays(1)).orElseThrow(() -> new IllegalArgumentException("Данные не верны"));
    }

    public List<Task> getWeekTasks() {
        log.info("Получение на текущую неделю...");

        LocalDate today = LocalDate.now();
        LocalDate monday = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate sunday = monday.plusDays(6);

        return taskRepository.findAllByDeadlineBetween(monday, sunday).orElseThrow(() -> new IllegalArgumentException("Данные не верны"));
    }

    public List<Task> getImportantTasks() {
        log.info("Получение важных задач...");
        return taskRepository.findAllByPriorityEquals(Priority.HIGH).orElseThrow(() -> new IllegalArgumentException("Данные не верны"));
    }
}
