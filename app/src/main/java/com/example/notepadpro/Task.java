package com.example.notepadpro;

public class Task {
    protected String title;
    protected String content;
    protected String dueDate;
    protected String dueTime;

    public Task(String title, String content, String dueDate) {
        this.title = title;
        this.content = content;
        this.dueDate = dueDate;
        this.dueTime = null;
    }

    public Task(String title, String content, String dueDate, String dueTime) {
        this.title = title;
        this.content = content;
        this.dueDate = dueDate;
        this.dueTime = dueTime;
    }
}
