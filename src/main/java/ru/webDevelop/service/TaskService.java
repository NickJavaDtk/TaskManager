package ru.webDevelop.service;

import org.springframework.beans.factory.annotation.Autowired;
import ru.webDevelop.entity.Task;
import ru.webDevelop.repository.TaskRepository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> findAll() {
        Iterable<Task> iterList = taskRepository.findAll();
        List<Task> taskList = new ArrayList<>();
        for (Iterator<Task> it = iterList.iterator( ); it.hasNext( ); ) {
            Task taskEntity = it.next( );
            taskList.add(taskEntity);
        }
        return taskList;
    }

    public Task findById(Long id) {
        Task task =  taskRepository.findById(id).orElse(null);
        return task;
    }

    public void saveTask(Task task) {
        taskRepository.save(task);
    }
}
