package com.alexj03.todo.service;

import com.alexj03.todo.dto.TaskDto;
import com.alexj03.todo.exception.TaskNotFoundException;
import com.alexj03.todo.exception.TasksNotFoundException;
import com.alexj03.todo.model.*;
import com.alexj03.todo.repository.TaskRepository;
import com.alexj03.todo.repository.UserRepository;
import com.alexj03.todo.specification.TaskSpecifications;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.*;
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

    public List<TaskDto> findTodayTasks(String title, Status status, Priority priority, Category category, String sortBy, String sortDir) {
        log.info("Find today tasks");

        Sort sort = Sort.by(Sort.Direction.fromString(sortDir), sortBy);

        Specification<Task> specification = Specification.where(TaskSpecifications.hasUser(userService.getCurrentUser()))
                .and(TaskSpecifications.hasTitle(title))
                .and(TaskSpecifications.hasStatus(status))
                .and(TaskSpecifications.hasPriority(priority))
                .and(TaskSpecifications.hasDeadline(LocalDate.now()))
                .and(TaskSpecifications.hasCategory(category));

        if ("priority".equalsIgnoreCase(sortBy)) {
            sort = Sort.by(Sort.Direction.fromString(sortDir), sortBy.equals("priority") ? "priorityOrder" : sortBy);
        }

        List<Task> tasks = taskRepository.findAll(specification, sort);

        return tasks.stream().map(this::toDto).toList();
    }

    public List<TaskDto> findTomorrowTasks(String title, Status status, Priority priority, Category category, String sortBy, String sortDir) {
        log.info("Find tomorrow tasks");

        Sort sort = Sort.by(Sort.Direction.fromString(sortDir), sortBy);

        Specification<Task> specification = Specification.where(TaskSpecifications.hasUser(userService.getCurrentUser()))
                .and(TaskSpecifications.hasTitle(title))
                .and(TaskSpecifications.hasStatus(status))
                .and(TaskSpecifications.hasPriority(priority))
                .and(TaskSpecifications.hasDeadline(LocalDate.now().plusDays(1)))
                .and(TaskSpecifications.hasCategory(category));

        if ("priority".equalsIgnoreCase(sortBy)) {
            sort = Sort.by(Sort.Direction.fromString(sortDir), sortBy.equals("priority") ? "priorityOrder" : sortBy);
        }

        List<Task> tasks = taskRepository.findAll(specification, sort);

        return tasks.stream().map(this::toDto).toList();
    }

    public List<TaskDto> findWeekTasks(String title, Status status, Priority priority, Category category, String sortBy, String sortDir) {
        log.info("Find week tasks");

        LocalDate today = LocalDate.now();
        LocalDate monday = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate sunday = monday.plusDays(6);

        Sort sort = Sort.by(Sort.Direction.fromString(sortDir), sortBy);

        Specification<Task> specification = Specification.where(TaskSpecifications.hasUser(userService.getCurrentUser()))
                .and(TaskSpecifications.hasTitle(title))
                .and(TaskSpecifications.hasStatus(status))
                .and(TaskSpecifications.hasPriority(priority))
                .and(TaskSpecifications.hasDeadlineBetween(monday, sunday))
                .and(TaskSpecifications.hasCategory(category));

        if ("priority".equalsIgnoreCase(sortBy)) {
            sort = Sort.by(Sort.Direction.fromString(sortDir), sortBy.equals("priority") ? "priorityOrder" : sortBy);
        }

        List<Task> tasks = taskRepository.findAll(specification, sort);

        return tasks.stream().map(this::toDto).toList();
    }

    public List<TaskDto> findImportantTasks(String title, Status status, Priority priority, Category category, String sortBy, String sortDir) {
        log.info("Find important tasks");

        Sort sort = Sort.by(Sort.Direction.fromString(sortDir), sortBy);

        Specification<Task> specification = Specification.where(TaskSpecifications.hasUser(userService.getCurrentUser()))
                .and(TaskSpecifications.hasTitle(title))
                .and(TaskSpecifications.hasStatus(status))
                .and(TaskSpecifications.hasPriority(Priority.HIGH))
                .and(TaskSpecifications.hasCategory(category));

        if ("priority".equalsIgnoreCase(sortBy)) {
            sort = Sort.by(Sort.Direction.fromString(sortDir), "priority");
        }

        List<Task> tasks = taskRepository.findAll(specification, sort);

        return tasks.stream().map(this::toDto).toList();
    }

    @Scheduled(cron = "0 0 0 * * ?")
    @EventListener(ContextRefreshedEvent.class)
    @Transactional
    public void deleteExpiredCompletedTasks() {
        log.info("Удаление просроченных выполненных задач");
        taskRepository.deleteByDeadlineBeforeAndStatusEquals(ZonedDateTime.now(ZoneOffset.UTC), Status.COMPLETED);
    }

    public TaskDto create(TaskDto taskDto) {
        log.info("Create task with title: {}", taskDto.getTitle());


        Task task = Task.builder()
                .createdAt(LocalDateTime.now())
                .title(taskDto.getTitle())
                .description(taskDto.getDescription())
                .status(taskDto.getStatus())
                .priority(taskDto.getPriority())
                .deadline(taskDto.getDeadline() != null ? taskDto.getDeadline() : null)
                .user(userService.getCurrentUser())
                .build();

        if (taskDto.getCategoryId() != null) {
            task.setCategory(categoryService.findById(taskDto.getCategoryId()));
        }

        taskRepository.save(task);

        return toDto(task);
    }

    public Task update(Long id, TaskDto taskDto) {
        log.info("Change task with id {}", id);
        log.info("Received deadline: {}", taskDto.getDeadline());

        Task task = Task.builder()
                .id(id)
                .title(taskDto.getTitle())
                .description(taskDto.getDescription())
                .priority(taskDto.getPriority())
                .deadline(taskDto.getDeadline())
                .status(taskDto.getStatus())
                .user(userService.getCurrentUser())
                .build();

        if (taskDto.getCategoryId() != null) {
            task.setCategory(categoryService.findById(taskDto.getCategoryId()));
        }

        taskRepository.save(task);
        return task;
    }

    public void delete(Long id) {
        log.info("Delete task with id {}", id);
        taskRepository.deleteById(id);
    }

    public List<TaskDto> findFilteredTasks(String title, Status status, Priority priority, LocalDate deadline, Category category, String sortBy, String sortDir) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDir), sortBy);

        Specification<Task> specification = Specification.where(TaskSpecifications.hasUser(userService.getCurrentUser()))
                .and(TaskSpecifications.hasTitle(title))
                .and(TaskSpecifications.hasStatus(status))
                .and(TaskSpecifications.hasPriority(priority))
                .and(TaskSpecifications.hasDeadline(deadline))
                .and(TaskSpecifications.hasCategory(category));

        if ("priority".equalsIgnoreCase(sortBy)) {
            sort = Sort.by(Sort.Direction.fromString(sortDir), sortBy.equals("priority") ? "priorityOrder" : sortBy);
        }

        List<Task> tasks = taskRepository.findAll(specification, sort);

        return tasks.stream().map(this::toDto).toList();
    }

    private TaskDto toDto(Task task) {
        TaskDto dto = TaskDto.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .priority(task.getPriority())
                .deadline(task.getDeadline())
                .status(task.getStatus())
                .userId(task.getUser().getId())
                .build();

        if (task.getCategory() != null) {
            dto.setCategoryId(task.getCategory().getId());
            dto.setCategoryTitle(task.getCategory().getTitle());
        }

        return dto;
    }
}





















