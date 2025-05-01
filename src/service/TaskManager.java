package service;

import model.Epic;
import model.SubTask;
import model.Task;

import java.util.Deque;
import java.util.List;

public interface TaskManager {
    Deque<Task> getHistory();

    List<Task> getTasks();

    List<Epic> getEpics();

    List<SubTask> getSubTasks();

    void removeAllTasks();

    void removeAllEpics();

    void removeAllSubTasks();

    Task getTaskById(int id);

    Epic getEpicById(int id);

    SubTask getSubTaskById(int id);

    List<SubTask> getAllSubTasksOfEpic(int id);

    void createTask(Task task);

    void createEpic(Epic epic);

    void createSubTask(SubTask subTask);

    void updateTask(Task task, int id);

    void updateEpic(Epic epic, int id);

    void updateSubTask(SubTask subTask, int id);

    void removeTaskById(int id);

    void removeEpicById(int id);

    void removeSubTaskById(int id);
}
