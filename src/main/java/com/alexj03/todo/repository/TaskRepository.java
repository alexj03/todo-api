package com.alexj03.todo.repository;

import com.alexj03.todo.model.Priority;
import com.alexj03.todo.model.Task;
import com.alexj03.todo.model.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long>, JpaSpecificationExecutor<Task> {

    Optional<List<Task>> findByTitle(String title);

    Optional<List<Task>> findAllByDeadline(LocalDate deadline);

    Optional<List<Task>> findAllByDeadlineBetween(LocalDate start, LocalDate end);

    Optional<List<Task>> findAllByUserAndPriorityEquals(User user, Priority priority);

    List<Task> findAllByUser(User user, Specification<Task> specification, Sort sort);
}
