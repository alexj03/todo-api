package com.alexj03.todo.repository;

import com.alexj03.todo.model.Priority;
import com.alexj03.todo.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {

    Optional<List<Task>> findByTitle(String title);

    Optional<List<Task>> findAllByDeadline(LocalDate deadline);

    Optional<List<Task>> findAllByDeadlineBetween(LocalDate start, LocalDate end);

    Optional<List<Task>> findAllByPriorityEquals(Priority priority);
}
