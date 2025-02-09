package com.alexj03.todo.exception;

public class TasksNotFoundException extends RuntimeException {

    public TasksNotFoundException() {
        super("Tasks not found");
    }
}
