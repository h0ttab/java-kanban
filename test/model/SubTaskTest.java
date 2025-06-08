package model;

import org.junit.jupiter.api.*;
import service.*;

import static org.junit.jupiter.api.Assertions.*;

class SubTaskTest {
    private static TaskManager taskManager;

    @BeforeAll
    static void initEnv() {
        taskManager = Managers.getDefault();
    }

    @Test
    @DisplayName("Две одинаковых подзадачи должны быть равны при сравнении через equals()")
    void shouldConsiderTwoIdenticalSubTasksEqual() {
        SubTask subTaskA = new SubTask("Тестовая подзадача",
                "Описание тестовой подзадачи", Status.NEW, 1);
        subTaskA.setId(1);

        SubTask subTaskB = new SubTask("Тестовая подзадача",
                "Описание тестовой подзадачи", Status.NEW, 1);
        subTaskB.setId(1);

        assertEquals(subTaskA, subTaskB);
    }

    @Test
    @DisplayName("Две одинаковых по содержанию подзадачи не должны быть равны при разных ID")
    void shouldNotConsiderTwoIdenticalSubTasksWithDifferentIdEqual() {
        SubTask subTaskA = new SubTask("Тестовая подзадача",
                "Описание тестовой подзадачи", Status.NEW, 1);
        subTaskA.setId(1);

        SubTask subTaskB = new SubTask("Тестовая подзадача",
                "Описание тестовой подзадачи", Status.NEW, 1);
        subTaskB.setId(2);

        assertNotEquals(subTaskA, subTaskB);
    }

    @Test
    @DisplayName("Нельзя добавить подзадачу в качестве своего эпика")
    void shouldNotLetAddingSubTaskAsItsOwnEpic() {
        taskManager.createEpic(new Epic("Эпик", "Описание эпика"));
        taskManager.createSubTask(new SubTask("Подзадача 1 эпика 1",
                "Описание подзадачи 1 эпика 1", Status.NEW, 1));

        assertThrows(NullPointerException.class,
                () -> taskManager.createSubTask(new SubTask("Подзадача 1 эпика 1",
                        "Описание подзадачи 1 эпика 1", Status.NEW, 2)));
    }

    @Test
    @DisplayName("Метод toCSV() возвращает корректную строку в формате CSV")
    void shouldReturnValidCSV() {
        SubTask subTask = new SubTask("Тестовая подзадача",
                "Описание тестовой подзадачи", Status.DONE, 4);
        subTask.setId(6);
        String expectedCSV = "6,SUBTASK,Тестовая подзадача,DONE,Описание тестовой подзадачи,4";

        assertEquals(expectedCSV, subTask.toCSV(6));
    }
}