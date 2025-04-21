import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {
    private final List<Integer> subTasksId = new ArrayList<>();

    public Epic(String title, String description) {
        super(title, description, Status.NEW);
    }

    public List<Integer> getSubTasksId() {
        if (!subTasksId.isEmpty()) {
            return subTasksId;
        }
        return null;
    }

    public void refreshStatus() {
        boolean isAllDone = true;
        boolean isAllNew = true;

        List<SubTask> subTasks = TaskManager.getSubTasksByEpicId(getId());

        if (subTasks.isEmpty()) {
            super.setStatus(Status.NEW);
            return;
        }
        for (SubTask subTask : subTasks) {
            if (subTask.getStatus() != Status.NEW) {
                isAllNew = false;
            }
            if (subTask.getStatus() != Status.DONE) {
                isAllDone = false;
            }
        }
        if (isAllDone) {
            super.setStatus(Status.DONE);
        } else if (isAllNew) {
            super.setStatus(Status.NEW);
        } else {
            super.setStatus(Status.IN_PROGRESS);
        }
    }

    public void addSubTask(int subTaskId){
        subTasksId.add(subTaskId);
        refreshStatus();
    }

    public void unlinkSubTask(int subTaskId) {
        int subTaskIndex = subTasksId.indexOf(subTaskId);
        subTasksId.remove(subTaskIndex);
        refreshStatus();
    }

    @Override
    public String toString() {
        return "Epic{" +
                "subTasksId=" + getSubTasksId() +
                ", id=" + getId() +
                ", status=" + getStatus() +
                '}';
    }
}
