package app;

import model.*;
import service.*;

public class Main {
    public static void main(String[] args) {
        TaskManager manager = Managers.getDefault();
        Task task = new Task("Задача", "Описание задачи", Status.NEW);
        Epic epic = new Epic("Эпик", "Описание эпика");
        SubTask subTask = new SubTask("Подзадача", "Описание подзадачи",
                Status.DONE, 2);

        manager.createTask(task);
        manager.createEpic(epic);
        manager.createSubTask(subTask);
        printAllTasks(manager);
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
