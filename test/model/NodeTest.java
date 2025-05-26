package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

public class NodeTest {
    Node<String> testNodeA = new Node<>("Node A");
    Node<String> testNodeB = new Node<>("Node B");
    Node<String> testNodeC = new Node<>("Node C");

    @BeforeEach
    void linkTestNodes() {
        testNodeA.next = testNodeB;
        testNodeB.prev = testNodeA;
        testNodeB.next = testNodeC;
        testNodeC.prev = testNodeB;
    }

    @Test
    @DisplayName("Узел A можно взаимно связать с узлом B")
    void shouldLinkNodes() {
        assertEquals(testNodeA.next, testNodeB);
        assertEquals(testNodeB.prev, testNodeA);
    }

    @Test
    @DisplayName("При удалении себя узел связывает соседние узлы")
    void shouldLinkNeighborsUponRemoval() {
        testNodeB.removeSelf();

        // Удаление узла с соседями в обе стороны
        assertEquals(testNodeA.next, testNodeC);
        assertEquals(testNodeC.prev, testNodeA);

        // Удаление узла с соседом только в одну сторону
        testNodeC.removeSelf();
        assertNull(testNodeA.next);
    }

    @Test
    @DisplayName("Два идентичных узла равны друг другу")
    void shouldBeEqualIfIdentical() {
        Node<String> copyOfTestNodeB = new Node<>("Node B");
        copyOfTestNodeB.prev = testNodeA;
        copyOfTestNodeB.next = testNodeC;

        // Намеренное использование assertTrue вместо assertEquals,
        // чтобы проверить метод equals()
        assertTrue(testNodeB.equals(copyOfTestNodeB));
        assertNotEquals(testNodeB, testNodeC);
    }
}
