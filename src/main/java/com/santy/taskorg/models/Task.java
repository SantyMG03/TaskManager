package com.santy.taskorg.models;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

public class Task {

    @NotNull(message = "Title cannot be empty")
    private String title;

    @NotNull(message = "Please select a date")
    @FutureOrPresent(message = "Date must be today or in the future")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String dueDate;

    public Task() {}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }
}
