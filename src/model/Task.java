package model;

import java.util.*;

public class Task {
    private final String title;
    private final String description;
    private final Status status;
    private int id;

    public Task(String title, String description, Status status) {
        this.title = title;
        this.description = description;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Status getStatus() {
        return status;
    }

    public String toCSV(int headersCount) {
        ArrayList<String> fields = new ArrayList<>(List.of(
                String.valueOf(id),
                "TASK",
                title,
                status.toString(),
                description));

        return String.join(",", fields);
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + getId() +
                ", title='" + getTitle() + '\'' +
                ", description.length='" + getDescription().length() + '\'' +
                ", status=" + getStatus() +
                '}';
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null) {
            return false;
        }
        if (this.getClass() != object.getClass()) {
            return false;
        }

        Task task = (Task) object;
        return (this.id == task.id
                && Objects.equals(this.title, task.title)
                && Objects.equals(this.description, task.description)
                && this.getStatus() == task.getStatus());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTitle(), getDescription(), getStatus());
    }
}
