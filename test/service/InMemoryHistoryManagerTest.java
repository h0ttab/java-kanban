package service;

import model.*;
import org.junit.jupiter.api.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InMemoryHistoryManagerTest {
    private HistoryManager historyManager;

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
}
