package com.alexj03.todo.exception;

public class TaskNotFoundException extends RuntimeException {

    public TaskNotFoundException(String title) {
        super("Task with title " + title + " not found");
    }

    public TaskNotFoundException(Long id) {
        super("Task with id + " + id + " not found");
    }
}
