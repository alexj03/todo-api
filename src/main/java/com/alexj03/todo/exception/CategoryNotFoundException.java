package com.alexj03.todo.exception;

public class CategoryNotFoundException extends RuntimeException {

    public CategoryNotFoundException(Long id) {
        super("Категория с id " + id + " не найдена");
    }
}
