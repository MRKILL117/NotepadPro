package com.example.notepadpro;

public class Task {
    protected String title;
    protected String content;
    protected String dueDate;

    public Task(String title, String content, String dueDate) {
        this.title = title;
        this.content = content;
        this.dueDate = dueDate;
    }
}
