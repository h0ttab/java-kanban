package service;

import model.*;
import org.junit.jupiter.api.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class ManagersTest {

    @Test
    @DisplayName("Класс Managers возвращает корректно работающий экземпляр менеджера задач")
    void shouldReturnCorrectlyInitializedTaskManager() {
        TaskManager taskManager = Managers.getDefault();
        assertNotNull(taskManager.getTasks());
        assertEquals(new ArrayList<Task>(), taskManager.getTasks());

        assertNotNull(taskManager.getEpics());
        assertEquals(new ArrayList<Epic>(), taskManager.getEpics());

        assertNotNull(taskManager.getSubTasks());
        assertEquals(new ArrayList<SubTask>(), taskManager.getSubTasks());

        assertNotNull(taskManager.getHistory());
        assertEquals(new LinkedList<>(), taskManager.getHistory());
    }

    @Test
    @DisplayName("Класс Managers возвращает корректно работающий экземпляр менеджера задач")
    void shouldReturnCorrectlyInitializedHistoryManager(){
        HistoryManager historyManager = Managers.getDefaultHistory();

        assertNotNull(historyManager.getHistory());
        assertEquals(new LinkedList<>(), historyManager.getHistory());
    }
}
