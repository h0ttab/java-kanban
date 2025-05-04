package service;

import model.*;
import org.junit.jupiter.api.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InMemoryHistoryManagerTest {
    private HistoryManager historyManager;
    private static final int HISTORY_MAX_SIZE = Managers.getDefaultHistory().getHistoryMaxSize();

    private static final Task task = new Task("Задача", "Описание задачи", Status.NEW);
    private static final Epic epic = new Epic("Эпик", "Описание эпика");
    private static final SubTask subTask = new SubTask("Подзадача", "Описание подзадачи",
            Status.DONE, 2);

    @BeforeEach
    void initHistoryManager() {
        historyManager = Managers.getDefaultHistory();
    }

    @Test
    @DisplayName("Класс InMemoryHistoryManager корректно добавляет задачи в историю просмотров")
    void shouldAddTasksToHistory() {
        historyManager.addTask(task);
        historyManager.addTask(epic);
        historyManager.addTask(subTask);

        Deque<Task> expectedHistory = new LinkedList<>(List.of(task, epic, subTask));
        assertEquals(expectedHistory, historyManager.getHistory());
    }

    @Test
    @DisplayName("Класс InMemoryHistoryManager корректно ограничивает размер истории просмотров задач")
    void shouldNotOverflowHistorySize() {
        for (int i = 0; i < HISTORY_MAX_SIZE + 1; i++) {
            historyManager.addTask(task);
        }
        assertEquals(HISTORY_MAX_SIZE, historyManager.getHistory().size());
    }

    @Test
    @DisplayName("Класс InMemoryHistoryManager корректно размещает элементы по принципу first in - first out")
    void shouldFollowFIFOOrder() {
        historyManager.addTask(subTask);
        for (int i = 0; i < HISTORY_MAX_SIZE - 2; i++) {
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
