package com.alexj03.todo.dto;

import com.alexj03.todo.model.Priority;
import com.alexj03.todo.model.Status;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class TodoDto {

    private String title;
    private String description;
    private Status status;
    private Priority priority;
    private LocalDateTime deadline;
}
