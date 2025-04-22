import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class Epic extends Task {
    private final Map<Integer, SubTask> subTasks = new HashMap<>();

    public Epic(String title, String description) {
        super(title, description, Status.NEW);
    }

    public Map<Integer, SubTask> getSubTasksMap() {
        return subTasks;
    }

    public List<SubTask> getSubTaskList() {
        return new ArrayList<>(subTasks.values());
    }

    public void addSubTask(SubTask subTask, int id) {
        subTask.setId(id);
        subTasks.put(id, subTask);
    }

    public void removeAllSubTasks(){
        subTasks.clear();
    }

    @Override
    public Status getStatus(){
        boolean isAllNew = true;
        boolean isAllDone = true;
        List<SubTask> subTaskList = new ArrayList<>(subTasks.values());

        if (subTaskList.isEmpty()) return Status.NEW;

        for (SubTask subTask : subTaskList) {
            Status status = subTask.getStatus();

            if (status != Status.NEW) {
                isAllNew = false;
            }
            if (status != Status.DONE) {
                isAllDone = false;
            }
        }

        if (isAllNew) return Status.NEW;
        if (isAllDone) return Status.DONE;
        return Status.IN_PROGRESS;
    }

    @Override
    public String toString() {
        return "Epic{" +
                "subTasksId=" + getSubTasksMap().keySet() +
                ", id=" + getId() +
                ", title='" + getTitle() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", status=" + getStatus() +
                '}';
    }
}
