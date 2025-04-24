package model;

import java.util.*;

public class Epic extends Task {
    private Map<Integer, Status> relatedSubTaskMap;

    public Epic(String title, String description) {
        super(title, description, Status.NEW);
        relatedSubTaskMap = new HashMap<>();
    }

    public List<Integer> getSubTaskIdList() {
        return new ArrayList<>(relatedSubTaskMap.keySet());
    }

    public void setRelatedSubTaskMap(Map<Integer, Status> relatedSubTaskMap) {
        this.relatedSubTaskMap = relatedSubTaskMap;
    }

    public Map<Integer, Status> getRelatedSubTaskMap() {
        return relatedSubTaskMap;
    }

    public void addSubTask(int id, Status status) {
        relatedSubTaskMap.put(id, status);
    }

    public void unlinkSubTask(int id) {
        if (relatedSubTaskMap.containsKey(id)) {
            relatedSubTaskMap.remove(id);
        } else {
            System.out.println("Ошибка при вызове unlinkSubTask(int id): В эпике id " + getId()
                    + " не найдена связанная подзадача с id " + id);
        }
    }

    public void unlinkAllSubtasks() {
        relatedSubTaskMap.clear();
    }

    public void updateSubTask(SubTask subTask) {
        if (relatedSubTaskMap.containsKey(subTask.getId())) {
            relatedSubTaskMap.put(subTask.getId(), subTask.getStatus());
        } else {
            System.out.println("Ошибка при вызове updateSubTask(SubTask subTask): В эпике id " + getId()
                    + " не найдена связанная подзадача с id " + subTask.getId());
        }
    }

    @Override
    public Status getStatus() {
        boolean isAllNew = true;
        boolean isAllDone = true;

        if (relatedSubTaskMap.isEmpty()) {
            return Status.NEW;
        }
        for (Status status : relatedSubTaskMap.values()) {
            if (status != Status.NEW) {
                isAllNew = false;
            }
            if (status != Status.DONE) {
                isAllDone = false;
            }
        }

        if (isAllNew) {
            return Status.NEW;
        }
        if (isAllDone) {
            return Status.DONE;
        } else {
            return Status.IN_PROGRESS;
        }
    }

    @Override
    public void setStatus(Status status) {
        System.out.println("Ошибка изменения статуса: "
                + "для эпиков ручное изменение статуса запрещено");
    }

    @Override
    public String toString() {
        return "Epic{" +
                "subTaskIdList=" + getSubTaskIdList() +
                ", id=" + super.getId() +
                ", title='" + super.getTitle() + '\'' +
                ", description.length='" + super.getDescription().length() + '\'' +
                ", status=" + getStatus() +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getRelatedSubTaskMap());
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

        Epic epic = (Epic) object;
        return this.relatedSubTaskMap.equals(epic.relatedSubTaskMap);
    }
}
