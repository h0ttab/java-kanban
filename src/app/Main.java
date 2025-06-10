package app;

import model.*;
import service.Managers;
import service.TaskManager;

public class Main {
    public static void main(String[] args) {
        TaskManager manager = getTaskManager();

        manager.getTaskById(2);
        System.out.println(manager.getHistory());

        manager.getEpicById(4);
        System.out.println(manager.getHistory());

        manager.getSubTaskById(6);
        System.out.println(manager.getHistory());

        manager.getTaskById(2);
        System.out.println(manager.getHistory());

        manager.getSubTaskById(7);
        System.out.println(manager.getHistory());

        manager.getSubTaskById(6);
        System.out.println(manager.getHistory());

        manager.getTaskById(2);
        System.out.println(manager.getHistory());

        manager.getEpicById(4);
        System.out.println(manager.getHistory());

        manager.removeTaskById(2);
        System.out.println(manager.getHistory());

        manager.removeEpicById(3);
        System.out.println(manager.getHistory());
    }

    private static TaskManager getTaskManager() {
        TaskManager manager = Managers.getDefault();

        Task taskA = new Task("Задача A", "Описание задачи A", Status.NEW);
        Task taskB = new Task("Задача B", "Описание задачи B", Status.DONE);
        Epic epicA = new Epic("Эпик A", "Описание эпика с тремя подзадачами");
        Epic epicB = new Epic("Эпик B", "Описание эпика без подзадач");
        SubTask subTaskA = new SubTask("Подзадача A1", "Подзадача 1 эпика A",
                Status.NEW, 3);
        SubTask subTaskB = new SubTask("Подзадача A2", "Подзадача 2 эпика A",
                Status.DONE, 3);
        SubTask subTaskC = new SubTask("Подзадача A3", "Подзадача 3 эпика A",
                Status.NEW, 3);

        manager.createTask(taskA); // id = 1
        manager.createTask(taskB); // id = 2
        manager.createEpic(epicA); // id = 3
        manager.createEpic(epicB); // id = 4
        manager.createSubTask(subTaskA); // id = 5
        manager.createSubTask(subTaskB); // id = 6
        manager.createSubTask(subTaskC); // id = 7
        return manager;
    }

    private static void printAllTasks(TaskManager manager) {
        System.out.println();
        System.out.println("Задачи:");
        for (Task task : manager.getTasks()) {
            System.out.println(task);
        }
        System.out.println();

        System.out.println("Эпики:");
        for (Task epic : manager.getEpics()) {
            System.out.println(epic);

            for (Task task : manager.getAllSubTasksOfEpic(epic.getId())) {
                System.out.println("--> " + task);
            }
        }
        System.out.println();

        System.out.println("Подзадачи:");
        for (Task subtask : manager.getSubTasks()) {
            System.out.println(subtask);
        }
        System.out.println();

        System.out.println("История:");
        for (Task task : manager.getHistory()) {
            System.out.println(task);
        }
        System.out.println();
    }
}