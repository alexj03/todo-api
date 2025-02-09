package com.alexj03.todo.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {

    private String token;

    private long expiresIn;
}
