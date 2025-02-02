package com.alexj03.todo.exception;

public class TaskNotFoundException extends Exception {

    public TaskNotFoundException(String message) {
        super(message);
    }
}
