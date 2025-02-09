package com.alexj03.todo.exception;

public class TaskNotFoundException extends RuntimeException {

    public TaskNotFoundException(String title) {
        super("Задача с заголовком " + title + " не найдена");
    }

    public TaskNotFoundException(Long id) {
        super("Задача с id + " + id + " не найдена");
    }
}
