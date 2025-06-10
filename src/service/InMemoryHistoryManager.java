package service;

import model.*;

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
        if (taskId == tail.value.getId()) {
            if (tail.prev != null) {
                tail = tail.prev;
                tail.next = null;
            } else {
                tail = null;
            }
            return;
        } else if (taskId == head.value.getId()) {
            if (head.next != null) {
                head = head.next;
                head.prev = null;
            } else {
                head = null;
            }
            return;
        }
        if (historyMap.containsKey(taskId)) {
            historyMap.remove(taskId).removeSelf();
        }
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

    static final class Node<T> {
        public Node<T> next;
        public Node<T> prev;
        public T value;

        public Node(T value) {
            this.value = value;
        }

        public void removeSelf() {
           Node<T> prevNode;
           Node<T> nextNode;

            if (next != null) {
                nextNode = next;
                nextNode.prev = prev;
            }

            if (prev != null) {
                prevNode = prev;
                prevNode.next = next;
            }
        }

        @Override
        public boolean equals(Object object) {
            if (this == object) return true;
            if (object == null || getClass() != object.getClass()) return false;
           Node<?> node = (Node<?>) object;
            return Objects.equals(next, node.next) && Objects.equals(prev, node.prev) && Objects.equals(value, node.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(next, prev, value);
        }

        @Override
        public String toString() {
            return "Node{" +
                    "value=" + value +
                    '}';
        }
    }
}