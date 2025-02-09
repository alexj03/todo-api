package com.alexj03.todo.controller;

import com.alexj03.todo.dto.CategoryDto;
import com.alexj03.todo.model.Category;
import com.alexj03.todo.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categories")
@Tag(name = "Category Controller", description = "Category Management Controller")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    @Operation(summary = "Finds all categories", description = "Finds all categories and returns all categories")
    public ResponseEntity<List<Category>> findAll() {
        return ResponseEntity.ok(categoryService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Finds category by id", description = "Finds category by id and returns it")
    public ResponseEntity<Category> findById(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.findById(id));
    }

    @PostMapping
    @Operation(summary = "Creates category", description = "Creates category and returns it")
    public ResponseEntity<Category> create(@RequestBody CategoryDto categoryDto) {
        return ResponseEntity.ok(categoryService.create(categoryDto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletes category by id", description = "Deletes category by id and returns nothing")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        categoryService.deleteById(id);
        return ResponseEntity.ok("Категория удалена");
    }

    @PutMapping("/{id}")
    @Operation(summary = "Rename category", description = "Rename category and returns updated category")
    public ResponseEntity<Category> rename(@PathVariable Long id, @RequestBody CategoryDto categoryDto) {
        return ResponseEntity.ok(categoryService.rename(id, categoryDto));
    }
}
