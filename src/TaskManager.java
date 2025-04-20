import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskManager {
    static private final Map<Integer, Task> tasks = new HashMap<>();
    static private final Map<Integer, Epic> epics = new HashMap<>();
    static private final Map<Integer, SubTask> subTasks = new HashMap<>();
    static public int taskId = 1;

    public static Task getTaskById(int taskId) {
        if (isValidId(taskId)) {
            return tasks.get(taskId);
        }
        return null;
    }

    public static Epic getEpicById(int epicId) {
        if (isValidId(epicId)) {
            return epics.get(epicId);
        }
        return null;
    }

    public static Task getSubTaskById(int subTaskId) {
        if (isValidId(subTaskId)) {
            return subTasks.get(taskId);
        }
        return null;
    }

    private static boolean isValidId(int taskId) {
        return tasks.containsKey(taskId) || epics.containsKey(taskId) || subTasks.containsKey(taskId);
    }

    public static List<SubTask> getSubTasksByEpicId(int epicId) {
        Epic epic = getEpicById(epicId);
        List<Integer> subTasksId = epic.getSubTasksId();

        List<SubTask> subTasksOfEpic = new ArrayList<>();

        for (Integer subTaskId : subTasks.keySet()) {
            if (subTasksId.contains(subTaskId)) {
                subTasksOfEpic.add(subTasks.get(subTaskId));
            }
        }
        return subTasksOfEpic;
    }

    public Map<Integer, Task> getTasks() {
        return tasks;
    }

    public Map<Integer, Epic> getEpics() {
        return epics;
    }

    public Map<Integer, SubTask> getSubTasks() {
        return subTasks;
    }

    public void clearTasks() {
        tasks.clear();
    }

    public void clearEpics() {
        epics.clear();
        subTasks.clear();
    }

    public void clearSubtasks() {
        subTasks.clear();
    }

    public void addTask(Task task) {
        tasks.put(task.getId(), task);
    }

    public void addEpic(Epic epic) {
        epic.refreshStatus();
        epics.put(epic.getId(), epic);
    }

    public void addSubTask(SubTask subTask) {
        subTasks.put(subTask.getId(), subTask);
    }

    public void updateTask(Task task) {
        tasks.put(task.getId(), task);
    }

    public void updateEpic(Epic epic) {
        tasks.put(epic.getId(), epic);
    }

    public void updateSubTask(SubTask subTask) {
        tasks.put(subTask.getId(), subTask);
    }

    public void removeTaskById(int taskId) {
        if (isValidId(taskId)) {
            tasks.remove(taskId);
        }
    }

    public void removeEpicById(int epicId) {
        Epic epic;

        if (isValidId(epicId)) {
            if (getEpicById(epicId) != null) {
                epic = getEpicById(epicId);

                for (Integer subTaskId : epic.getSubTasksId()) {
                    removeSubTaskById(subTaskId);
                }

                epics.remove(epicId);
            }
        }
    }

    public void removeSubTaskById(int subTaskId) {
        if (isValidId(subTaskId)) {
            subTasks.remove(subTaskId);
        }
    }
}
