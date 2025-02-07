package com.alexj03.todo.repository;

import com.alexj03.todo.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findAllByTitle(String title);
}
