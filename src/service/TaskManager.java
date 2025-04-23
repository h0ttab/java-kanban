package service;

import model.Epic;
import model.Status;
import model.SubTask;
import model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskManager {
    private final Map<Integer, Task> allTasks = new HashMap<>();
    private final Map<Integer, Epic> allEpics = new HashMap<>();
    private final Map<Integer, SubTask> allSubTasks = new HashMap<>();
    private final IdGenerator idGenerator;

    public TaskManager(IdGenerator idGenerator){
        this.idGenerator = idGenerator;
    }

    public List<Task> getAllTasksList() {
        return new ArrayList<>(allTasks.values());
    }

    public List<Epic> getAllEpicsList() {
        return new ArrayList<>(allEpics.values());
    }

    public List<SubTask> getAllSubTasksList() {
        return new ArrayList<>(allSubTasks.values());
    }

    public void removeAllTasks() {
        allTasks.clear();
    }

    public void removeAllEpics() {
        removeAllSubTasks();
        allEpics.clear();
    }

    public void removeAllSubTasks() {
        List<Epic> epics = getAllEpicsList();

        for (Epic epic : epics) {
            epic.unlinkAllSubtasks();
        }
        allSubTasks.clear();
    }

    public Task getTaskById(int id) {
        if (allTasks.containsKey(id)) {
            return allTasks.get(id);
        } else {
            throw new IllegalArgumentException("Задачи с id " + id + " не существует");
        }
    }

    public Epic getEpicById(int id) {
        if (allEpics.containsKey(id)) {
            return allEpics.get(id);
        } else {
            throw new IllegalArgumentException("Эпика с id " + id + " не существует");
        }
    }

    public SubTask getSubTaskById(int id) {
        if (allSubTasks.containsKey(id)) {
            return allSubTasks.get(id);
        } else {
            throw new IllegalArgumentException("Подзадачи с id " + id + " не существует");
        }
    }

    public List<SubTask> getAllSubTasksOfEpic(int id) {
        List<SubTask> subTaskList = new ArrayList<>();
        Epic epic = getEpicById(id);

        for (Integer subTaskId : epic.getSubTaskIdList()) {
            subTaskList.add(getSubTaskById(subTaskId));
        }
        return subTaskList;
    }

    public void createTask(Task task) {
        int newId = idGenerator.generateUniqueId();

        task.setId(newId);
        allTasks.put(newId, task);
    }

    public void createEpic(Epic epic) {
        int newId = idGenerator.generateUniqueId();

        epic.setId(newId);
        allEpics.put(newId, epic);
    }

    public void createSubTask(SubTask subTask) {
        Epic relatedEpic = getEpicById(subTask.getEpicId());
        int newId = idGenerator.generateUniqueId();

        subTask.setId(newId);
        relatedEpic.addSubTask(subTask.getId(), subTask.getStatus());
        allSubTasks.put(subTask.getId(), subTask);
    }

    public void updateTask(Task task, int id) {
        if (allTasks.containsKey(id)) {
            task.setId(id);
            allTasks.put(id, task);
        } else {
            throw new IllegalArgumentException("Ошибка обновления задачи id " + id + " - задача не найдена");
        }
    }

    public void updateEpic(Epic epic, int id) {
        Epic epicOld = getEpicById(id);
        Map<Integer, Status> bufferedMap = new HashMap<>(epicOld.getRelatedSubTaskMap());

        epic.setRelatedSubTaskMap(bufferedMap);
        epic.setId(id);
        allEpics.put(id, epic);
    }

    public void updateSubTask(SubTask subTask, int id) {
        if (allSubTasks.containsKey(id)) {
            SubTask oldSubTask = getSubTaskById(id);
            Epic oldEpic = getEpicById(oldSubTask.getEpicId());
            Epic newEpic = getEpicById(subTask.getEpicId());

            subTask.setId(id);

            if (newEpic.equals(oldEpic)) {
                newEpic.updateSubTask(subTask);
            } else {
                oldEpic.unlinkSubTask(id);
                newEpic.addSubTask(id, subTask.getStatus());
            }
            allSubTasks.put(id, subTask);
        } else {
            throw new IllegalArgumentException("Ошибка обновления подзадачи id " + id + " - подзадача не найдена");
        }
    }

    public void removeTaskById(int id) {
        if (allTasks.containsKey(id)) {
            allTasks.remove(id);
        } else {
            throw new IllegalArgumentException("Невозможно удалить задачу id "
                    + id + " по id: задача не найдена.");
        }
    }

    public void removeEpicById(int id) {
        if (allEpics.containsKey(id)) {
            Epic epic = getEpicById(id);
            List<Integer> relatedSubTasks = epic.getSubTaskIdList();

            for (Integer subTaskId : relatedSubTasks) {
                removeSubTaskById(subTaskId);
            }
            allEpics.remove(id);
        } else {
            throw new IllegalArgumentException("Невозможно удалить эпик id "
                    + id + " по id: эпик не найден.");
        }
    }

    public void removeSubTaskById(int id) {
        if (allSubTasks.containsKey(id)) {
            SubTask subTask = getSubTaskById(id);
            Epic epic = getEpicById(subTask.getEpicId());

            epic.unlinkSubTask(id);
            allSubTasks.remove(id);
        } else {
            throw new IllegalArgumentException("Невозможно удалить подзадачу id "
                    + id + " по id: подзадача не найдена.");
        }
    }
}