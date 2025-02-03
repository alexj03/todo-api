package com.alexj03.todo.repository;

import com.alexj03.todo.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TodoRepository extends JpaRepository<Todo, Long> {

    Optional<List<Todo>> findByTitle(String title);
}
