import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskManager {
    private int id = 1;

    private final Map<Integer, Task> taskList = new HashMap<>();
    private final Map<Integer, Epic> epicList = new HashMap<>();

    public int getUniqueId() {
        return id++;
    }

    public void createTask(Task task, int id) {
        task.setId(id);
        taskList.put(id, task);
    }

    public void createEpic(Epic epic, int id) {
        epic.setId(id);
        epicList.put(id, epic);
    }

    public List<Task> getTaskList() {
        return new ArrayList<>(taskList.values());
    }

    public List<Epic> getEpicList() {
        return new ArrayList<>(epicList.values());
    }

    public List<SubTask> getSubTaskList() {
        List<Epic> epicList = getEpicList();
        List<SubTask> subTaskList = new ArrayList<>();

        for (Epic epic : epicList) {
            List<SubTask> subTasksOfEpic = epic.getSubTaskList();
            subTaskList.addAll(subTasksOfEpic);
        }

        return subTaskList;
    }

    public void removeAllTasks(){
        taskList.clear();
    }

    public void removeAllEpics(){
        epicList.clear();
    }

    public void removeAllSubTasks(){
        List<Epic> epicList = getEpicList();

        for (Epic epic : epicList) {
            epic.removeAllSubTasks();
        }
    }

    public void removeSubTask(int id) {
        List <Epic> epicList = getEpicList();

        for (Epic epic : epicList) {
            Map <Integer, SubTask> subTaskMap = epic.getSubTasksMap();
            subTaskMap.remove(id);
        }
    }

    public void removeTask(int id) {
        taskList.remove(id);
    }

    public void removeEpic(int id) {
        epicList.remove(id);
    }

    public Task getTask(int id){
        if (taskList.containsKey(id)) return taskList.get(id);
        return null;
    }

    public Epic getEpic(int id) {
        if (epicList.containsKey(id)) return epicList.get(id);
        return null;
    }

    public SubTask getSubTask(int id){
        List <Epic> epicList = getEpicList();

        for (Epic epic : epicList) {
            Map <Integer, SubTask> subTaskMap = epic.getSubTasksMap();
            if (subTaskMap.containsKey(id)) return subTaskMap.get(id);
        }

        return null;
    }

    public void updateTask(int id, Task task){
        if (taskList.containsKey(id)) taskList.put(id, task);
    }

    public void updateEpic(int id, Epic epic){
        if (epicList.containsKey(id)) epicList.put(id, epic);
    }

    public void updateSubTask(int id, SubTask subTask) {
        SubTask oldSubTask = getSubTask(id);
        Epic epic = oldSubTask.getEpic();
        epic.addSubTask(subTask, id);
    }

    public List<SubTask> getEpicSubTasks(int id){
        Epic epic = getEpic(id);
        return epic.getSubTaskList();
    }
}
