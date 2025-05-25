package model;

import java.util.Objects;

public class Node<T> {
    Node<T> next;
    Node<T> prev;
    T value;

    public Node(T value) {
        this.value = value;
    }

    public void removeSelf() {
        Node<T> prevNode;
        Node<T> nextNode;

        if (next != null) {
            nextNode = next;
            nextNode.linkPrev(prev);
        }

        if (prev != null) {
            prevNode = prev;
            prevNode.linkNext(next);
        }
    }

    public void linkNext(Node<T> node) {
        next = node;
    }

    public void linkPrev(Node<T> node) {
        prev = node;
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
