package com.alexj03.todo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tasks")
public class Task {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Task id", example = "1")
    private Long id;

    @Column(name = "title")
    @Schema(description = "Task title", example = "Buy Groceries for the Week")
    @NotBlank(message = "The title must not be blank")
    private String title;

    @Column(name = "description")
    @Schema(description = "Task description", example = "Make a shopping list, go to the store, and buy everything needed for the week")
    private String description;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    @Schema(description = "Task status", example = "IN_PROGRESS")
    private Status status;

    @Column(name = "priority")
    @Enumerated(EnumType.STRING)
    @Schema(description = "Task priority", example = "HIGH")
    private Priority priority;

    @Column(name = "created_at")
    @Schema(description = "Task creation date", example = "2025-02-06 13:30:45.963820")
    private LocalDateTime createdAt;

    @Column(name = "deadline")
    @Schema(description = "Task deadline", example = "2025-02-12")
    private LocalDate deadline;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    @JsonBackReference
    @Schema(description = "Task's category", example = "Shopping & Groceries")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;
}
