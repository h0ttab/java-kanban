package model;

import org.junit.jupiter.api.*;
import service.*;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class EpicTest {
    private static Epic epicA;
    private static Epic epicB;

    @BeforeEach
    void initEnv() {
        epicA = new Epic("Тестовый эпик", "Описание тестового эпика");
        epicB = new Epic("Тестовый эпик", "Описание тестового эпика");
        epicA.setId(1);
        epicB.setId(2);
    }

    @Test
    @DisplayName("Два одинаковых эпика должны быть равны при сравнении через equals()")
    void shouldConsiderTwoIdenticalTasksEqual() {
        epicA.setId(1);
        epicB.setId(1);
        assertEquals(epicA, epicB);
    }

    @Test
    @DisplayName("Два одинаковых по содержимому эпика не должны быть равны при разных ID")
    void shouldNotConsiderTwoIdenticalTasksWithDifferentIdEqual() {
        assertNotEquals(epicA, epicB);
    }

    @Test
    @DisplayName("Эпик нельзя добавить в самого себя в виде подзадачи")
    void shouldNowAllowAddingEpicAsItsOwnSubtask() {
        assertNull(epicA.addSubTask(1, Status.NEW));
    }

    @Test
    @DisplayName("Вычисление статуса эпика происходит корректно")
    void shouldUpdateEpicStatusCorrectly() {
        TaskManager taskManager = Managers.getDefault();

        taskManager.createEpic(epicA);

        assertEquals(Status.NEW, taskManager.getEpicById(1).getStatus());

        taskManager.createSubTask(new SubTask("Подзадача",
                "Описание подзадачи", Status.NEW, 1));
        taskManager.createSubTask(new SubTask("Подзадача",
                "Описание подзадачи", Status.NEW, 1));
        assertEquals(Status.NEW, taskManager.getEpicById(1).getStatus());

        taskManager.updateSubTask(new SubTask("Подзадача",
                "Описание подзадачи", Status.DONE, 1), 2);
        assertEquals(Status.IN_PROGRESS, taskManager.getEpicById(1).getStatus());

        taskManager.updateSubTask(new SubTask("Подзадача",
                "Описание подзадачи", Status.DONE, 1), 3);
        assertEquals(Status.DONE, taskManager.getEpicById(1).getStatus());
    }

    @Test
    @DisplayName("Конструктор с параметром статуса корректно создаёт эпик с нужным статусом")
    void shouldCreateEpicWithStatus() {
        Status expectedStatus = Status.DONE;
        Epic testEpicWithStatus = new Epic("Тестовый эпик", "Описание тестового эпика", expectedStatus);

        try {
            Class<?> superclassTask = testEpicWithStatus.getClass().getSuperclass();

            Field statusField = superclassTask.getDeclaredField("status");
            statusField.setAccessible(true);

            Object fieldValue = statusField.get(testEpicWithStatus);
            Status actualStatus = (Status) fieldValue;

            assertEquals(expectedStatus, actualStatus);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    @DisplayName("Метод toCSV() возвращает корректную строку в формате CSV")
    void shouldReturnValidCSV() {
        String expectedCSV = "1,EPIC,Тестовый эпик,NEW,Описание тестового эпика";
        assertEquals(expectedCSV, epicA.toCSV(5));
    }

}