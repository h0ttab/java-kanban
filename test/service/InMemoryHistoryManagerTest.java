package service;

import model.*;
import org.junit.jupiter.api.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class InMemoryHistoryManagerTest {
    private static final Task task = new Task("Задача", "Описание задачи", Status.NEW);
    private static final Epic epic = new Epic("Эпик", "Описание эпика");
    private static final SubTask subTask = new SubTask("Подзадача", "Описание подзадачи",
            Status.DONE, 2);
    private HistoryManager historyManager;

    @BeforeEach
    void initHistoryManager() {
        historyManager = Managers.getDefaultHistory();
        task.setId(1);
        epic.setId(2);
        subTask.setId(3);
        historyManager.addTask(task);
        historyManager.addTask(epic);
        historyManager.addTask(subTask);
    }

    @Test
    @DisplayName("Класс InMemoryHistoryManager корректно добавляет задачи в историю просмотров")
    void shouldAddTasksToHistory() {
        List<Task> expectedHistory = new LinkedList<>(List.of(task, epic, subTask));
        assertEquals(expectedHistory, historyManager.getHistory());
    }

    @Test
    @DisplayName("Задачи корректно удаляются из истории по ID задачи")
    void shouldRemoveViewRecordByTaskId() {
        List<Task> expectedHistory = new ArrayList<>(List.of(task, subTask));
        historyManager.remove(2);

        assertEquals(expectedHistory, historyManager.getHistory());
    }

    @Test
    @DisplayName("История не содержит дубликатов и хранит только самый свежий просмотр задачи")
    void shouldKeepOnlyUniqueLatestViews() {
        // Обновление записи из середины истории
        Task updatedEpic = new Epic("Эпик", "Обновлённое описание эпика");
        updatedEpic.setId(2);
        List<Task> expectedHistory = new ArrayList<>(List.of(task, subTask, updatedEpic));

        historyManager.addTask(updatedEpic);

        assertEquals(expectedHistory, historyManager.getHistory());

        // Обновление записи из начала истории (head node)
        initHistoryManager();
        Task updatedTask = new Task("Задача", "Описание задачи", Status.DONE);
        updatedTask.setId(1);
        expectedHistory = new ArrayList<>(List.of(epic, subTask, updatedTask));

        historyManager.addTask(updatedTask);

        assertEquals(expectedHistory, historyManager.getHistory());

        // Обновление записи из конца истории (tail node)
        initHistoryManager();
        Task updatedSubTask = new SubTask("Подзадача", "Описание подзадачи",
                Status.NEW, 2);
        updatedSubTask.setId(3);
        expectedHistory = new ArrayList<>(List.of(task, epic, updatedSubTask));

        historyManager.addTask(updatedSubTask);

        assertEquals(expectedHistory, historyManager.getHistory());
    }
}
