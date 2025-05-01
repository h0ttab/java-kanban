package service;

import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class InMemoryHistoryManagerTest {
    private HistoryManager historyManager;
    private final Task task = new Task("Задача", "Описание задачи", Status.NEW);
    private final Epic epic = new Epic("Эпик", "Описание эпика");
    private final SubTask subTask = new SubTask("Подзадача", "Описание подзадачи",
            Status.DONE, 2);

    @BeforeEach
    void initEnv(){
        historyManager = Managers.getDefaultHistory();
    }

    @Test
    @DisplayName("Класс InMemoryHistoryManager корректно добавляет задачи в историю просмотров")
    void shouldAddTasksToHistory() {
        historyManager.addTask(task);
        historyManager.addTask(epic);
        historyManager.addTask(subTask);

        Deque <Task> expectedHistory = new LinkedList<>(List.of(task, epic, subTask));
        assertEquals(expectedHistory, historyManager.getHistory());
    }

    @Test
    @DisplayName("Класс InMemoryHistoryManager корректно ограничивает свой размер в 10 элементов")
    void shouldNotOverflowSizeOf10() {
        for (int i = 0; i < 11; i++) {
            historyManager.addTask(task);
        }

        int expectedHistorySize = 10;
        assertEquals(expectedHistorySize, historyManager.getHistory().size());
    }

    @Test
    @DisplayName("Класс InMemoryHistoryManager корректно размещает элементы по принципу first in - first out")
    void shouldFollowFIFOOrder() {
        historyManager.addTask(subTask);
        for (int i = 0; i < 8; i++) {
            historyManager.addTask(task);
        }
        historyManager.addTask(epic);
        assertEquals(subTask, historyManager.getHistory().getFirst());
        assertEquals(epic, historyManager.getHistory().getLast());

        historyManager.addTask(subTask);
        assertEquals(task, historyManager.getHistory().getFirst());
        assertEquals(subTask, historyManager.getHistory().getLast());
    }
}
