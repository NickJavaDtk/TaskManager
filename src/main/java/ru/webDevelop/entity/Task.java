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
    @OneToMany(mappedBy = "taskResponsible")
    public List<Responsible> responsibleList;
    @Temporal(TemporalType.DATE)
    private LocalDate deadLine;
    private Boolean complete;

    public Task() {
    }

    public Task(String name, String description, Appeal appeal, List<Responsible> responsibleList, LocalDate deadLine, Boolean complete) {
        this.name = name;
        this.description = description;
        this.appeal = appeal;
        this.responsibleList = responsibleList;
        this.deadLine = deadLine;
        this.complete = complete;
    }

    public Task(Long id, String name, String description, Appeal appeal, List<Responsible> responsibleList, LocalDate deadLine, Boolean complete) {
        Id = id;
        this.name = name;
        this.description = description;
        this.appeal = appeal;
        this.responsibleList = responsibleList;
        this.deadLine = deadLine;
        this.complete = complete;
    }
}
