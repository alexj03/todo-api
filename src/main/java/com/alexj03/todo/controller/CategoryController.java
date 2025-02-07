package com.alexj03.todo.controller;

import com.alexj03.todo.dto.CategoryDto;
import com.alexj03.todo.model.Category;
import com.alexj03.todo.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/category")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/")
    public ResponseEntity<List<Category>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.getById(id));
    }

    @PostMapping("/")
    public ResponseEntity<Category> create(@RequestBody CategoryDto categoryDto) {
        return ResponseEntity.ok(categoryService.create(categoryDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        categoryService.deleteById(id);
        return ResponseEntity.ok("Категория удалена");
    }

    @PutMapping("/")
    public ResponseEntity<Category> update(@RequestBody CategoryDto categoryDto) {
        return ResponseEntity.ok(categoryService.update(categoryDto));
    }
}
