package ru.webDevelop.repository;

import org.springframework.data.repository.CrudRepository;
import ru.webDevelop.entity.Task;

public interface TaskRepository extends CrudRepository<Task, Long> {
}
