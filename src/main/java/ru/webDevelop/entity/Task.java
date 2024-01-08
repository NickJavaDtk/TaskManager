package ru.webDevelop.entity;



import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
public class Task {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;
    private String name;
    private String description;
    @OneToOne(mappedBy = "taskAppeal", cascade = CascadeType.ALL)
    private Appeal appeal;
    @ManyToMany
    @JoinTable(
            name="task_executor",
            joinColumns = @JoinColumn(name="task_id"),
            inverseJoinColumns = @JoinColumn(name = "executor_id")
    )
    public List<Executor> executorList;
    @Temporal(TemporalType.DATE)
    private LocalDate deadLine;
    private Boolean complete;

    public Task() {
    }

    public Task(String name, String description, Appeal appeal, List<Executor> executorList, LocalDate deadLine, Boolean complete) {
        this.name = name;
        this.description = description;
        this.appeal = appeal;
        this.executorList = executorList;
        this.deadLine = deadLine;
        this.complete = complete;
    }

    public Task(Long id, String name, String description, Appeal appeal, List<Executor> executorList, LocalDate deadLine, Boolean complete) {
        Id = id;
        this.name = name;
        this.description = description;
        this.appeal = appeal;
        this.executorList = executorList;
        this.deadLine = deadLine;
        this.complete = complete;
    }
}
