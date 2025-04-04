package com.alexj03.todo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryDto {

    @Schema(description = "Category title", example = "Shopping & Groceries")
    @NotBlank(message = "The title must not be blank")
    private String title;
}
