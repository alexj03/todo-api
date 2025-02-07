package com.alexj03.todo.service;

import com.alexj03.todo.dto.CategoryDto;
import com.alexj03.todo.model.Category;
import com.alexj03.todo.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryService {


    private final CategoryRepository categoryRepository;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category create(CategoryDto categoryDto) {
        Category category = Category.builder().title(categoryDto.getTitle()).build();
        return categoryRepository.save(category);
    }

    public Category getById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Неверные данные"));
    }

    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }

    public Category update(CategoryDto categoryDto) {
        Category category = categoryRepository.findAllByTitle(categoryDto.getTitle()).orElseThrow(() -> new IllegalArgumentException("Неверные данные"));
        return categoryRepository.save(category);
    }
}
