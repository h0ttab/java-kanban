package service;

import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

public class InMemoryTaskManagerTest {
    private final TaskManager taskManager = Managers.getDefault();
    Task task = new Task("Задача", "Описание задачи", Status.NEW);
    Epic epic = new Epic("Эпик", "Описание эпика");
    SubTask subTask = new SubTask("Подзадача", "Описание подзадачи",
            Status.DONE, 2);

    @BeforeEach
    void addTasksOfEachType(){
        taskManager.createTask(task);// id=1
        taskManager.createEpic(epic); // id=2
        taskManager.createSubTask(subTask); // id=3, epicId=2
    }

    @Test
    @DisplayName("Класс InMemoryTaskManager корректно получает задачи (добавлены в @BeforeEach методе)")
    void shouldAddTasksCorrectly(){
        assertEquals(task, taskManager.getTaskById(1));
        assertEquals(epic, taskManager.getEpicById(2));
        assertEquals(subTask, taskManager.getSubTaskById(3));
        assertEquals(subTask.getEpicId(), 2);
    }

    @Test
    @DisplayName("Класс InMemoryTaskManager обеспечивает неизменность задачи при добавлении")
    void shouldNotModifyTaskWhenAdded(){
        Task task = new Task("Оригинальная задача", "Описание", Status.NEW);
        int addedTaskId = taskManager.createTask(task);

        assertEquals(task, taskManager.getTaskById(addedTaskId));
    }

    @Test
    @DisplayName("Класс InMemoryTaskManager корректно удаляет задачи")
    void shouldDeleteTaskCorrectly(){
        taskManager.removeTaskById(1);
        assertNull(taskManager.getTaskById(1));
    }

    @Test
    @DisplayName("Класс InMemoryTaskManager корректно удаляет эпики и подзадачи")
    void shouldDeleteEpicCorrectly(){
        taskManager.removeEpicById(2);
        assertNull(taskManager.getEpicById(2));
        assertNull(taskManager.getSubTaskById(3));
    }

    @Test
    @DisplayName("Класс InMemoryTaskManager корректно удаляет подзадачу из эпика")
    void shouldDeleteSubTaskCorrectly(){
        taskManager.removeSubTaskById(3);
        assertNull(taskManager.getSubTaskById(3));
        assertTrue(taskManager.getEpicById(2).getSubTaskIdList().isEmpty());
    }

    @Test
    @DisplayName("Класс InMemoryTaskManager корректно обновляет задачи")
    void shouldUpdateTasksCorrectly() {
        taskManager.updateTask(new Task("Задача новая", "Описание другой задачи",
                Status.NEW),1);
        assertNotEquals(task, taskManager.getTaskById(1));

        taskManager.updateEpic(new Epic("Эпик новый", "Описание другого эпика"),2);
        assertNotEquals(task, taskManager.getEpicById(2));
        assertEquals(subTask.getId(), taskManager.getEpicById(2).getSubTaskIdList().getFirst());

        taskManager.updateSubTask(new SubTask("Подзадача новая", "Описание другой подзадачи",
                Status.NEW, 2),3);
        assertNotEquals(subTask, taskManager.getSubTaskById(3));
    }

    @Test
    @DisplayName("История просмотров сохраняет состояние задачи на момент её просмотра")
    void shouldPreserveTaskStateAsWhenAdded() {
        TaskManager taskManager = Managers.getDefault();
        Task originalTask = new Task("Оригинал", "Описание оригинала", Status.NEW);

        int addedTaskId = taskManager.createTask(originalTask);
        taskManager.getTaskById(1);

        Task updatedTask = new Task("Уже не оригинал","Другое описание", Status.IN_PROGRESS);
        taskManager.updateTask(updatedTask, addedTaskId);

        assertEquals(updatedTask ,taskManager.getTaskById(1));
        assertEquals(taskManager.getHistory().getFirst(), originalTask);
        assertEquals(taskManager.getHistory().getLast(), updatedTask);
    }

    @Test
    @DisplayName("InMemoryTaskManager проверяет сгенерированный ID на уникальность при добавлении задачи")
    void shouldAvoidExistingIdConflict(){
        int existingTaskId = taskManager.createTask(task);
        Task existingTask = taskManager.getTaskById(existingTaskId);

        Task newTask = new Task("Задача", "Описание", Status.NEW);
        newTask.setId(existingTaskId);
        int newTaskId = taskManager.createTask(newTask);

        assertNotEquals(existingTaskId, newTaskId);
        assertEquals(existingTask, taskManager.getTaskById(existingTaskId));
        assertEquals(newTask, taskManager.getTaskById(newTaskId));
    }

}
