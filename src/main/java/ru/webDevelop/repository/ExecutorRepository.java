package ru.webDevelop.repository;

import org.springframework.data.repository.CrudRepository;
import ru.webDevelop.entity.Executor;

import java.util.List;

public interface ExecutorRepository extends CrudRepository<Executor, Long> {
    Executor findByUsername(String username);
    List<Executor> findAll();
}
