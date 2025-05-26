package service;

import model.Node;
import model.Task;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {
    private final Map<Integer, Node<Task>> historyMap = new HashMap<>();
    private Node<Task> head;
    private Node<Task> tail;

    private void linkLast(Node<Task> node) {
        if (tail == null) {
            head = node;
        } else {
            tail.next = node;
            node.prev = tail;
        }
        tail = node;
    }

    public void remove(int taskId) {
        historyMap.get(taskId).removeSelf();
    }

    @Override
    public List<Task> getHistory() {
        List<Task> history = new ArrayList<>();
        Node<Task> pointer = head;

        while (pointer != null) {
            history.add(pointer.value);
            pointer = pointer.next;
        }

        return history;
    }

    @Override
    public void addTask(Task task) {
        Integer taskId = task.getId();

        if (historyMap.containsKey(taskId)) {
            remove(taskId);
        }

        Node<Task> newHistoryRecord = new Node<>(task);
        historyMap.put(taskId, newHistoryRecord);
        linkLast(newHistoryRecord);
    }

}
