package com.alexj03.todo.service;

import com.alexj03.todo.dto.TaskDto;
import com.alexj03.todo.exception.TaskNotFoundException;
import com.alexj03.todo.exception.TasksNotFoundException;
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

    public List<Task> findAll() {
        log.info("Получить все задачи");
        return taskRepository.findAll();
    }

    public Task findById(Long id) {
        log.info("Получить задачу с id {}", id);
        return taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
    }

    public List<Task> findByTitle(String title) {
        log.info("Получить задачу по названию {}", title);
        return taskRepository.findByTitle(title)
                .orElseThrow(() -> new TaskNotFoundException(title));
    }

    public List<Task> findTodayTasks() {
        log.info("Получить задачи на сегодня");
        return taskRepository.findAllByDeadline(LocalDate.now()).orElseThrow(() -> new TasksNotFoundException("На сегодня задач нет"));
    }

    public List<Task> findTomorrowTasks() {
        log.info("Получить задачи на завтра");
        return taskRepository.findAllByDeadline(LocalDate.now().plusDays(1)).orElseThrow(() -> new TasksNotFoundException("На завтра задач нет"));
    }

    public List<Task> findWeekTasks() {
        log.info("Получить задачи на текущую неделю");

        LocalDate today = LocalDate.now();
        LocalDate monday = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate sunday = monday.plusDays(6);

        return taskRepository.findAllByDeadlineBetween(monday, sunday).orElseThrow(() -> new TasksNotFoundException("На неделю задач нет"));
    }

    public List<Task> findImportantTasks() {
        log.info("Получить список задач с высоким приоритетом");
        return taskRepository.findAllByPriorityEquals(Priority.HIGH).orElseThrow(() -> new TasksNotFoundException("Задач с высоким приоритетом нет"));
    }

    public Task create(TaskDto taskDto) {
        log.info("Создать задачу с названием {}", taskDto.getTitle());

        Category category = categoryService.findById(taskDto.getCategoryId());

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
        return task;
    }

    public Task update(Long id, TaskDto taskDto) {
        log.info("Изменить задачу с id {}", id);

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

    public void delete(Long id) {
        log.info("Удалить задачу с id {}", id);
        taskRepository.deleteById(id);
    }
}
