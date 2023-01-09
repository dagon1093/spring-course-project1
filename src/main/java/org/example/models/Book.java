package org.example.models;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

public class Book {

    private int id;
    private Integer person_id;
    @NotEmpty(message = "Should not be empty")
    private String title;
    @NotEmpty(message = "Should not be empty")
    private String author;
    @Min(value = 0)
    private int year;

    public Book() {
    }

    public Book(int id, int person_id, String title, String author, int year) {
        this.id = id;
        this.person_id = person_id;
        this.title = title;
        this.author = author;
        this.year = year;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getPerson_id() {
        return person_id;
    }

    public void setPerson_id(Integer person_id) {
        this.person_id = person_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
