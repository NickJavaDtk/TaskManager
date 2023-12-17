package ru.webDevelop.entity;

import jakarta.persistence.*;


@Entity
public class Appeal {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;
    private String phone;
    private String name;
    @Lob
    private byte[] image;
    private String description;
    @OneToOne
    private Task taskAppeal;

    public Appeal() {
    }

    public Appeal(Long id, String phone, String name, String description) {
        Id = id;
        this.phone = phone;
        this.name = name;
        this.description = description;
    }

    public Appeal(Long id, String phone, String name, byte[] image, String description) {
        Id = id;
        this.phone = phone;
        this.name = name;
        this.image = image;
        this.description = description;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
