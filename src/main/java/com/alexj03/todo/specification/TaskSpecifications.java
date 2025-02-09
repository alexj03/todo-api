package com.alexj03.todo.specification;

import com.alexj03.todo.model.Category;
import com.alexj03.todo.model.Priority;
import com.alexj03.todo.model.Status;
import com.alexj03.todo.model.Task;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class TaskSpecifications {

    public static Specification<Task> hasTitle(String title) {
        return (root, query, builder) -> title == null ? null :
                builder.like(builder.lower(root.get("title")), "%" + title.toLowerCase() + "%");
    }

    public static Specification<Task> hasStatus(Status status) {
        return (root, query, builder) -> status == null ? null :
                builder.equal(root.get("status"), status);
    }

    public static Specification<Task> hasPriority(Priority priority) {
        return (root, query, builder) -> priority == null ? null :
                builder.equal(root.get("priority"), priority);
    }

    public static Specification<Task> hasDeadline(LocalDate deadline) {
        return (root, query, builder) -> deadline == null ? null :
                builder.equal(root.get("deadline"), deadline);
    }

    public static Specification<Task> hasCategory(Category category) {
        return (root, query, builder) -> category == null ? null :
                builder.equal(root.get("category"), category);
    }
}
