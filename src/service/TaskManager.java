package service;

import model.*;

import java.util.*;

public interface TaskManager {
    List<Task> getHistory();

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

    int createTask(Task task);

    int createEpic(Epic epic);

    int createSubTask(SubTask subTask);

    void updateTask(Task task, int id);

    void updateEpic(Epic epic, int id);

    void updateSubTask(SubTask subTask, int id);

    void removeTaskById(int id);

    void removeEpicById(int id);

    void removeSubTaskById(int id);
}
