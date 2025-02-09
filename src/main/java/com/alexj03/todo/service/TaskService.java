package com.alexj03.todo.service;

import com.alexj03.todo.dto.TaskDto;
import com.alexj03.todo.exception.TaskNotFoundException;
import com.alexj03.todo.exception.TasksNotFoundException;
import com.alexj03.todo.model.*;
import com.alexj03.todo.repository.TaskRepository;
import com.alexj03.todo.repository.UserRepository;
import com.alexj03.todo.specification.TaskSpecifications;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
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
    private final UserRepository userRepository;
    private final UserService userService;

    public List<Task> findAll() {
        log.info("Find all tasks");
        return taskRepository.findAll();
    }

    public Task findById(Long id) {
        log.info("Find task with id {}", id);
        return taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
    }

    public List<Task> findByTitle(String title) {
        log.info("Find task with title {}", title);
        return taskRepository.findByTitle(title)
                .orElseThrow(() -> new TaskNotFoundException(title));
    }

    public List<Task> findTodayTasks() {
        log.info("Find today tasks");
        return taskRepository.findAllByDeadline(LocalDate.now()).orElseThrow(TasksNotFoundException::new);
    }

    public List<Task> findTomorrowTasks() {
        log.info("Find tomorrow tasks");
        return taskRepository.findAllByDeadline(LocalDate.now().plusDays(1)).orElseThrow(TasksNotFoundException::new);
    }

    public List<Task> findWeekTasks() {
        log.info("Find week tasks");

        LocalDate today = LocalDate.now();
        LocalDate monday = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate sunday = monday.plusDays(6);

        return taskRepository.findAllByDeadlineBetween(monday, sunday).orElseThrow(TasksNotFoundException::new);
    }

    public List<Task> findImportantTasks() {
        log.info("Find important tasks");
        return taskRepository.findAllByPriorityEquals(Priority.HIGH).orElseThrow(TasksNotFoundException::new);
    }

    public Task create(TaskDto taskDto) {
        log.info("Create task with title: {}", taskDto.getTitle());

        Task task = Task.builder()
                .createdAt(LocalDateTime.now())
                .title(taskDto.getTitle())
                .description(taskDto.getDescription())
                .status(taskDto.getStatus())
                .priority(taskDto.getPriority())
                .deadline(taskDto.getDeadline() != null ? taskDto.getDeadline().plusDays(1) : null)
                .user(userService.getCurrentUser())
                .build();

        taskRepository.save(task);
        return task;
    }

    public Task update(Long id, TaskDto taskDto) {
        log.info("Change task with id {}", id);

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
        log.info("Delete task with id {}", id);
        taskRepository.deleteById(id);
    }

    public List<Task> findFilteredTasks(String title, Status status, Priority priority, LocalDate deadline, Category category, String sortBy, String sortDir) {
        log.info("Find tasks by title, status, priority, deadline, category: {}, {}, {}, {}, {}", title, status, priority, deadline, category);

        Sort sort = Sort.by(Sort.Direction.fromString(sortDir), sortBy);

        Specification<Task> specification = Specification.where(TaskSpecifications.hasUser(userService.getCurrentUser()))
                .and(TaskSpecifications.hasTitle(title))
                .and(TaskSpecifications.hasStatus(status))
                .and(TaskSpecifications.hasPriority(priority))
                .and(TaskSpecifications.hasDeadline(deadline))
                .and(TaskSpecifications.hasCategory(category));

        return taskRepository.findAll(specification, sort);
    }
}





















