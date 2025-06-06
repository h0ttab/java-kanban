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
    public String toCSV(int headersCount){
        return super.toCSV(headersCount).replace("TASK", "SUBTASK") + "," + epicId;
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
        if (object == null) {
            return false;
        }
        if (this.getClass() != object.getClass()) {
            return false;
        }
        if (!super.equals(object)) {
            return false;
        }

        SubTask subTask = (SubTask) object;
        return this.epicId == subTask.epicId;
    }
}
