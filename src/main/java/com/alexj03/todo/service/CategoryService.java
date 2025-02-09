package com.alexj03.todo.service;

import com.alexj03.todo.dto.CategoryDto;
import com.alexj03.todo.exception.CategoryNotFoundException;
import com.alexj03.todo.model.Category;
import com.alexj03.todo.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Category findById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException(id));
    }

    public Category create(CategoryDto categoryDto) {
        Category category = Category.builder().title(categoryDto.getTitle()).build();
        return categoryRepository.save(category);
    }

    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }

    public Category rename(Long id, CategoryDto categoryDto) {
        Category category = findById(id);
        category.setTitle(categoryDto.getTitle());
        return categoryRepository.save(category);
    }
}
