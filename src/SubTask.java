public class SubTask extends Task {
    private final int linkedEpicId;

    public SubTask(String title, String description, Status status, int linkedEpicId) {
        super(title, description, status);
        this.linkedEpicId = linkedEpicId;
        TaskManager.getEpicById(linkedEpicId).addSubTask(getId());
    }

    public void removeSubTask(){
        Epic epic = TaskManager.getEpicById(linkedEpicId);
        epic.unlinkSubTask(getId());
    }

    public int getLinkedEpicId() {
        return linkedEpicId;
    }

    @Override
    public void setStatus(Status status) {
        super.setStatus(status);
        Epic epic = TaskManager.getEpicById(linkedEpicId);
        epic.refreshStatus();
    }

    @Override
    public String toString() {
        return "SubTask{" +
                "linkedEpicId=" + linkedEpicId +
                ", id=" + getId() +
                ", status=" + getStatus() +
                '}';
    }
}
