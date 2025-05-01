package service;

import model.Task;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {
    private final Deque<Task> viewsHistory = new LinkedList<>();

    @Override
    public Deque<Task> getHistory() {
        return viewsHistory;
    }

    @Override
    public void addTask(Task task) {
        if (viewsHistory.size() >= 10) {
            viewsHistory.removeFirst();
        }
        viewsHistory.addLast(task);
    }

}
