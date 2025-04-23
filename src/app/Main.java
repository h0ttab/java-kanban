package app;

import model.Epic;
import model.Status;
import model.SubTask;
import model.Task;
import service.TaskManager;

public class Main {
    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();

        taskManager.createTask(
                new Task("Обычная задача 1", "Описание обычной задачи 1", Status.NEW)
        );
        taskManager.createTask(
                new Task("Обычная задача 2", "Описание обычной задачи 2 Описание обычной задачи 2", Status.NEW)
        );

        Epic epic1 = new Epic("Эпик 1", "Описание эпика 1");
        taskManager.createEpic(epic1);
        taskManager.createSubTask(new SubTask("Подзадача 1 эпика 1",
                "Описание поздачи 1 эпика 1", Status.NEW, epic1.getId()));
        taskManager.createSubTask(new SubTask("Подзадача 2 эпика 1",
                "Описание поздачи 2 эпика 1 Описание поздачи 2 эпика 1", Status.NEW, epic1.getId()));

        Epic epic2 = new Epic("Эпик 2", "Описание эпика 2");
        taskManager.createEpic(epic2);
        taskManager.createSubTask(new SubTask("Подзадача 1 эпика 2",
                "Описание поздачи 2 эпика 1", Status.NEW, epic2.getId()));

        printAllTasks(taskManager);
        System.out.println();

        taskManager.updateTask(new Task("Обычная задача 1", "Описание обычной задачи 1", Status.DONE), 1);
        taskManager.updateSubTask(new SubTask("Подзадача 1 эпика 1",
                "Описание поздачи 1 эпика 1", Status.DONE, epic1.getId()), 4);

        printAllTasks(taskManager);
        System.out.println();

        taskManager.removeTaskById(2);
        taskManager.removeSubTaskById(5);

        printAllTasks(taskManager);
    }

    public static void printAllTasks(TaskManager taskManager) {
        System.out.println(taskManager.getAllTasksList());
        System.out.println(taskManager.getAllEpicsList());
        System.out.println(taskManager.getAllSubTasksList());
    }
}
