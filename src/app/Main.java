package app;

import model.*;
import service.*;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        TaskManager taskManager = Managers.getDefault();

        taskManager.createTask(
                new Task("Обычная задача 1", "Описание обычной задачи 1", Status.NEW)
        );
        taskManager.createTask(
                new Task("Обычная задача 2", "Описание обычной задачи 2 Описание обычной задачи 2",
                        Status.NEW)
        );

        Epic epic1 = new Epic("Эпик 1", "Описание эпика 1");
        taskManager.createEpic(epic1);

        taskManager.createSubTask(new SubTask("Подзадача 1 эпика 1",
                "Описание подзадачи 1 эпика 1", Status.NEW, epic1.getId()));
        taskManager.createSubTask(new SubTask("Подзадача 2 эпика 1",
                "Описание подзадачи 2 эпика 1 Описание подзадачи 2 эпика 1", Status.NEW, epic1.getId()));

        Epic epic2 = new Epic("Эпик 2", "Описание эпика 2");
        taskManager.createEpic(epic2);

        taskManager.createSubTask(new SubTask("Подзадача 1 эпика 2",
                "Описание подзадачи 2 эпика 1", Status.NEW, epic2.getId()));

        printAllTasks(taskManager);

        taskManager.updateTask(new Task("Обычная задача 1", "Описание обычной задачи 1", Status.DONE)
                , 1);
        taskManager.updateSubTask(new SubTask("Подзадача 1 эпика 1",
                "Описание подзадачи 1 эпика 1", Status.DONE, epic1.getId()), 4);

        printAllTasks(taskManager);

        taskManager.removeTaskById(2);
        taskManager.removeSubTaskById(5);

        printAllTasks(taskManager);

        printHistoryIdOnly(taskManager);

        taskManager.getTaskById(1);
        printHistoryIdOnly(taskManager);
        taskManager.getEpicById(3);
        printHistoryIdOnly(taskManager);
        taskManager.getSubTaskById(7);
        printHistoryIdOnly(taskManager);
        taskManager.getSubTaskById(7);
        printHistoryIdOnly(taskManager);
        taskManager.getTaskById(1);
        printHistoryIdOnly(taskManager);
        taskManager.getEpicById(3);
        printHistoryIdOnly(taskManager);

    }

    public static void printAllTasks(TaskManager taskManager) {
        System.out.println(taskManager.getTasks());
        System.out.println(taskManager.getEpics());
        System.out.println(taskManager.getSubTasks());
        System.out.println();
    }

    public static void printHistoryIdOnly(TaskManager taskManager) {
        Deque<Task> history = taskManager.getHistory();
        List<Integer> ids = new ArrayList<>();

        for (Task task : history) {
            ids.add(task.getId());
        }

        System.out.println(ids);
    }
}
