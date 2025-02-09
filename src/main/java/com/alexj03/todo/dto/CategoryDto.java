package com.alexj03.todo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CategoryDto {

    @Schema(description = "Category title", example = "Shopping & Groceries")
    @NotBlank(message = "The title must not be blank")
    private String title;
}
