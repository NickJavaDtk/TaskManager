package ru.webDevelop.entity;

public class UserClass {
    private Long id;
    private String name;
    private String song;
    private String author;

    public UserClass(Long id, String name, String song, String author) {
        this.id = id;
        this.name = name;
        this.song = song;
        this.author = author;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSong() {
        return song;
    }

    public void setSong(String song) {
        this.song = song;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
