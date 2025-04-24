package model;

import java.util.Objects;

public class SubTask extends Task {
    private final int epicId;

    public SubTask(String title, String description, Status status, int epicId) {
        super(title, description, status);
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }

    @Override
    public String toString() {
        return "SubTask{" +
                "epicId=" + getEpicId() +
                ", id=" + super.getId() +
                ", title='" + super.getTitle() + '\'' +
                ", description.length='" + super.getDescription().length() + '\'' +
                ", status=" + super.getStatus() +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getEpicId());
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null) {
            return false;
        }
        if (this.getClass() != object.getClass()){
            return false;
        }

        SubTask subTask = (SubTask) object;
        return this.epicId == subTask.epicId
                && this.getId() == subTask.getId()
                && Objects.equals(this.getTitle(), subTask.getTitle())
                && Objects.equals(this.getDescription(), subTask.getDescription())
                && this.getStatus() == subTask.getStatus();
    }
}
