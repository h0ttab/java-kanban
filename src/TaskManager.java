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

    public static SubTask getSubTaskById(int subTaskId) {
        if (isValidId(subTaskId)) {
            return subTasks.get(subTaskId);
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
        if (subTasksId != null){
            for (Integer subTaskId : subTasks.keySet()) {
                if (subTasksId.contains(subTaskId)) {
                    subTasksOfEpic.add(subTasks.get(subTaskId));
                }
            }
        }
        return subTasksOfEpic;
    }

    public static Map<Integer, Task> getTasks() {
        return tasks;
    }

    public static Map<Integer, Epic> getEpics() {
        return epics;
    }

    public static Map<Integer, SubTask> getSubTasks() {
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

    public static void addTask(Task task) {
        tasks.put(task.getId(), task);
    }

    public static void addEpic(Epic epic) {
        epics.put(epic.getId(), epic);
    }

    public static void addSubTask(SubTask subTask) {
        subTasks.put(subTask.getId(), subTask);
        Epic epic = getEpicById(subTask.getLinkedEpicId());
        epic.refreshStatus();
    }

    public static void updateTask(Task task) {
        tasks.put(task.getId(), task);
    }

    public static void updateEpic(Epic epic) {
        tasks.put(epic.getId(), epic);
    }

    public static void updateSubTask(SubTask subTask) {
        tasks.put(subTask.getId(), subTask);
        Epic epic = getEpicById(subTask.getLinkedEpicId());
        epic.refreshStatus();
    }

    public static void removeTaskById(int taskId) {
        if (isValidId(taskId)) {
            tasks.remove(taskId);
        }
    }

    public static void removeEpicById(int epicId) {
        Epic epic;
        if (isValidId(epicId)) {
            if (getEpicById(epicId) != null) {
                epic = getEpicById(epicId);
                List <Integer> subTaskList = epic.getSubTasksId();

                for (int i = 0; i < subTaskList.size(); i++) {
                    removeSubTaskById(subTaskList.get(i));
                }

                epics.remove(epicId);
            }
        }
    }

    public static void removeSubTaskById(int subTaskId) {
        if (isValidId(subTaskId)) {
            SubTask subTask = getSubTaskById(subTaskId);
            subTask.removeSubTask();
            subTasks.remove(subTaskId);
        }
    }
}
