public class SubTask extends Task {
    private final int linkedEpicId;

    public SubTask(String title, String description, Status status, int linkedEpicId) {
        super(title, description, status);
        this.linkedEpicId = linkedEpicId;
    }

    @Override
    public void setStatus(Status status) {
        super.setStatus(status);
        TaskManager.getEpicById(linkedEpicId).refreshStatus();
    }
}
