package service;

import model.*;

import java.util.*;

public class InMemoryTaskManager implements TaskManager {
    private final Map<Integer, Task> allTasks = new HashMap<>();
    private final Map<Integer, Epic> allEpics = new HashMap<>();
    private final Map<Integer, SubTask> allSubTasks = new HashMap<>();
    private final IdGenerator idGenerator;
    private final HistoryManager historyManager;

    public InMemoryTaskManager(IdGenerator idGenerator, HistoryManager historyManager) {
        this.idGenerator = idGenerator;
        this.historyManager = historyManager;
    }

    private int generateUniqueId(Map<Integer, ?> storage) {
        int id;
        do {
            id = idGenerator.generateId();
        } while (storage.containsKey(id));
        return id;
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

    @Override
    public List<Task> getTasks() {
        return new ArrayList<>(allTasks.values());
    }

    @Override
    public List<Epic> getEpics() {
        return new ArrayList<>(allEpics.values());
    }

    @Override
    public List<SubTask> getSubTasks() {
        return new ArrayList<>(allSubTasks.values());
    }

    @Override
    public void removeAllTasks() {
        allTasks.clear();
    }

    @Override
    public void removeAllEpics() {
        removeAllSubTasks();
        allEpics.clear();
    }

    @Override
    public void removeAllSubTasks() {
        List<Epic> epics = getEpics();

        for (Epic epic : epics) {
            epic.unlinkAllSubtasks();
        }
        allSubTasks.clear();
    }

    @Override
    public Task getTaskById(int id) {
        if (allTasks.containsKey(id)) {
            Task task = allTasks.get(id);
            historyManager.addTask(task);
            return task;
        }
        System.out.println("Ошибка при вызове getTaskById(int id): Задачи с id " + id + " не существует");
        return null;
    }

    @Override
    public Epic getEpicById(int id) {
        if (allEpics.containsKey(id)) {
            Epic epic = allEpics.get(id);
            historyManager.addTask(epic);
            return epic;
        } else {
            System.out.println("Ошибка при вызове getEpicById(int id): Эпика с id " + id + " не существует");
            return null;
        }
    }

    @Override
    public SubTask getSubTaskById(int id) {
        if (allSubTasks.containsKey(id)) {
            SubTask subTask = allSubTasks.get(id);
            historyManager.addTask(subTask);
            return subTask;
        } else {
            System.out.println("Ошибка при вызове getSubTaskById(int id): Подзадачи с id " + id + " не существует");
            return null;
        }
    }

    @Override
    public List<SubTask> getAllSubTasksOfEpic(int id) {
        List<SubTask> subTaskList = new ArrayList<>();
        Epic epic = getEpicById(id);

        for (Integer subTaskId : epic.getSubTaskIdList()) {
            subTaskList.add(getSubTaskById(subTaskId));
        }
        return subTaskList;
    }

    @Override
    public int createTask(Task task) {
        int newId = generateUniqueId(allTasks);
        task.setId(newId);
        allTasks.put(newId, task);

        return newId;
    }

    @Override
    public int createEpic(Epic epic) {
        int newId = generateUniqueId(allEpics);
        epic.setId(newId);
        allEpics.put(newId, epic);

        return newId;
    }

    @Override
    public int createSubTask(SubTask subTask) {
        int newId = generateUniqueId(allSubTasks);
        subTask.setId(newId);

        Epic relatedEpic = getEpicById(subTask.getEpicId());
        relatedEpic.addSubTask(subTask.getId(), subTask.getStatus());
        allSubTasks.put(newId, subTask);

        return newId;
    }

    @Override
    public void updateTask(Task task, int id) {
        if (allTasks.containsKey(id)) {
            task.setId(id);
            allTasks.put(id, task);
        } else {
            System.out.println("Ошибка при вызове updateTask(Task task, int id): "
                    + "Ошибка обновления задачи id " + id + " - задача не найдена");
        }
    }

    @Override
    public void updateEpic(Epic epic, int id) {
        Epic epicOld = getEpicById(id);
        Map<Integer, Status> bufferedMap = new HashMap<>(epicOld.getRelatedSubTaskMap());

        epic.setRelatedSubTaskMap(bufferedMap);
        epic.setId(id);
        allEpics.put(id, epic);
    }

    @Override
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
            System.out.println("Ошибка при вызове updateSubTask(SubTask subTask, int id): "
                    + "Ошибка обновления подзадачи id " + id + " - подзадача не найдена");
        }
    }

    @Override
    public void removeTaskById(int id) {
        if (allTasks.containsKey(id)) {
            allTasks.remove(id);
            historyManager.remove(id);
        } else {
            System.out.println("Ошибка при вызове removeTaskById(int id): Невозможно удалить задачу id "
                    + id + " по id: задача не найдена.");
        }
    }

    @Override
    public void removeEpicById(int id) {
        if (allEpics.containsKey(id)) {
            Epic epic = getEpicById(id);
            List<Integer> relatedSubTasks = epic.getSubTaskIdList();

            for (Integer subTaskId : relatedSubTasks) {
                removeSubTaskById(subTaskId);
            }
            allEpics.remove(id);
            historyManager.remove(id);
        } else {
            throw new IllegalArgumentException("Ошибка при вызове removeEpicById(int id): Невозможно удалить эпик id "
                    + id + " по id - эпик не найден.");
        }
    }

    @Override
    public void removeSubTaskById(int id) {
        if (allSubTasks.containsKey(id)) {
            SubTask subTask = getSubTaskById(id);
            Epic epic = getEpicById(subTask.getEpicId());

            epic.unlinkSubTask(id);
            allSubTasks.remove(id);
            historyManager.remove(id);
        } else {
            System.out.println("Ошибка при вызове removeSubTaskById(int id): Невозможно удалить подзадачу id "
                    + id + " по id: подзадача не найдена.");
        }
    }
}