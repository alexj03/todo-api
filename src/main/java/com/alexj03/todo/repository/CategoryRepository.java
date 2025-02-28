package com.alexj03.todo.repository;

import com.alexj03.todo.model.Category;
import com.alexj03.todo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<List<Category>> findAllByUser(User user);
}