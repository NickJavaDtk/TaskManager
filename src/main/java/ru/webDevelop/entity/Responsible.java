package ru.webDevelop.entity;


import jakarta.persistence.*;
import org.springframework.context.annotation.Role;



@Entity
public class Responsible {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private  String username;
    private  String password;
    private String name;
    private String position;
    @ManyToOne
    @JoinColumn(name = "responsibleList")
    private Task taskResponsible;
    @Transient
    private Role role;

    public Responsible(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Responsible() {
    }

    public Responsible(Long id, String username, String password, String name, String position, Role role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.position = position;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
