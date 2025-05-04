package service;

import model.Task;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {
    private final Deque<Task> viewsHistory = new LinkedList<>();
    private final int HISTORY_MAX_SIZE = 10;

    @Override
    public Deque<Task> getHistory() {
        return viewsHistory;
    }

    @Override
    public void addTask(Task task) {
        if (viewsHistory.size() >= HISTORY_MAX_SIZE) {
            viewsHistory.removeFirst();
        }
        viewsHistory.addLast(task);
    }

    @Override
    public int getHistoryMaxSize() {
        return HISTORY_MAX_SIZE;
    }

}
