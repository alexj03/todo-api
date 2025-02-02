package com.alexj03.todo.service;

import com.alexj03.todo.dto.TodoDto;
import com.alexj03.todo.model.Todo;
import com.alexj03.todo.exception.TaskNotFoundException;
import com.alexj03.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;

    public List<Todo> getAll() {
        log.info("Получение списка задач...");
        return todoRepository.findAll();
    }

    public Todo getById(Long id) throws TaskNotFoundException {
        log.info("Получение задачи под идентификатором {}...", id);
        return todoRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Задача с идентификатором " + id + " не найдена!"));
    }

    public void create(TodoDto todoDto) {
        log.info("Создание задачи с названием {}...", todoDto.getTitle());
        Todo todo = Todo.builder()
                .title(todoDto.getTitle())
                .description(todoDto.getDescription())
                .build();

        todoRepository.save(todo);
    }

    public void delete(Long id) {
        log.info("Удаление задачи под идентификатором {}...", id);
        todoRepository.deleteById(id);
    }

    public void update(Todo todo) {
        log.info("Изменение задачи под идентификатором {}...", todo.getId());
        todoRepository.save(todo);
    }

    public Todo getByTitle(String title) throws TaskNotFoundException {
        return todoRepository.findByTitle(title)
                .orElseThrow(() -> new TaskNotFoundException("Задачи с заголовком " + title + " не существует!"));
    }
}
