package model;

import java.util.Objects;

public class Node<T> {
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
