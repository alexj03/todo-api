package com.alexj03.todo.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "categories")
public class Category {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Category id", example = "1")
    private Long id;

    @Column(name = "title")
    @Schema(description = "Category title", example = "Shopping & Groceries")
    @NotBlank(message = "The title must not be blank")
    private String title;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "category", fetch = FetchType.LAZY)
    @JsonManagedReference
    @Schema(description = "Category's tasks", example = "Buy Groceries for the Week")
    private List<Task> tasks = new ArrayList<>();
}
