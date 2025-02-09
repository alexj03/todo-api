package com.alexj03.todo.dto;

import com.alexj03.todo.model.Priority;
import com.alexj03.todo.model.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TaskDto {

    @Schema(description = "Task title", example = "Buy Groceries for the Week")
    @NotBlank(message = "The title must not be blank")
    private String title;

    @Schema(description = "Task description", example = "Make a shopping list, go to the store, and buy everything needed for the week")
    private String description;

    @Schema(description = "Task status", example = "IN_PROGRESS")
    private Status status;

    @Schema(description = "Task priority", example = "HIGH")
    private Priority priority;

    @Schema(description = "Task deadline", example = "2025-02-10")
    private LocalDate deadline;

    @Schema(description = "Category id", example = "1")
    private Long categoryId;
}
