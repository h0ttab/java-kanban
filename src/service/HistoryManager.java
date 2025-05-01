package service;

import model.Task;

import java.util.Deque;

public interface HistoryManager {
    void addTask(Task task);

    Deque<Task> getHistory();
}
