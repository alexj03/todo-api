package com.alexj03.todo;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
        info = @Info(
                title = "To-Do REST API",
                version = "1.0",
                description = "API for managing To-Do application",
                contact = @Contact(name = "Alexander Feoktistov", email = "afeoktistovvv@gmail.com", url = "https://github.com/alexj03")
        )
)
@SpringBootApplication
public class TodoApplication {

    public static void main(String[] args) {
        SpringApplication.run(TodoApplication.class, args);
    }

}
