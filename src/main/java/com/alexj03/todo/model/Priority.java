package com.alexj03.todo.model;

public enum Priority {
    HIGH(1),
    MIDDLE(2),
    LOW(3),
    NONE(4);

    private final int order;

    Priority(int order) {
        this.order = order;
    }

    public int getOrder() {
        return order;
    }
}