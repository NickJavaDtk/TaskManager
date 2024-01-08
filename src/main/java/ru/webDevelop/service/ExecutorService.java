package ru.webDevelop.service;

import org.springframework.stereotype.Service;
import ru.webDevelop.entity.Executor;
import ru.webDevelop.repository.ExecutorRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ExecutorService {
   private final ExecutorRepository repository;


    public ExecutorService(ExecutorRepository repository) {
        this.repository = repository;
    }

    public List<Executor> findAllExecutor() {
        List<Executor> list = Optional.of(repository.findAll()).orElse(null);
        return list;
    }
}
