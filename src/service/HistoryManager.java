package service;

import java.util.List;

import model.Task;

public interface HistoryManager {
    void addTask(Task task);

    List<Task> getHistory();

    void remove(int id);
}