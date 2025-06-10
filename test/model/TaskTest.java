package model;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    @Test
    @DisplayName("Две одинаковые задачи должны быть равны при сравнении через equals()")
    void shouldConsiderTwoIdenticalTasksEqual() {

        Task taskA = new Task("Тестовая задача", "Описание тестовой задачи", Status.NEW);
        taskA.setId(1);
        Task taskB = new Task("Тестовая задача", "Описание тестовой задачи", Status.NEW);
        taskB.setId(1);

        assertEquals(taskA, taskB);
    }

    @Test
    @DisplayName("Две одинаковые по содержимому задачи не должны быть равны при разных ID")
    void shouldNotConsiderTwoIdenticalTasksWithDifferentIdEqual() {

        Task taskA = new Task("Тестовая задача", "Описание тестовой задачи", Status.NEW);
        taskA.setId(1);
        Task taskB = new Task("Тестовая задача", "Описание тестовой задачи", Status.NEW);
        taskB.setId(2);

        assertNotEquals(taskA, taskB);
    }

    @Test
    @DisplayName("Метод toCSV() возвращает корректную строку в формате CSV")
    void shouldReturnValidCSV() {
        Task taskA = new Task("Тестовая задача", "Описание тестовой задачи", Status.NEW);
        taskA.setId(1);
        String expectedCSV = "1,TASK,Тестовая задача,NEW,Описание тестовой задачи";

        assertEquals(expectedCSV, taskA.toCSV(5));
    }

}