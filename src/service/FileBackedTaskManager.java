package service;

import model.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;

import service.exceptions.ManagerSaveException;

public class FileBackedTaskManager extends InMemoryTaskManager implements TaskManager {
    private final Path saveFilePath;
    private final String[] headers = new String[]{"id", "type", "name", "status", "description", "epic"};

    public FileBackedTaskManager(IdGenerator idGenerator, HistoryManager historyManager, Path saveFilePath ) {
        super(idGenerator, historyManager);
        this.saveFilePath = saveFilePath;
    }

    public void save() throws ManagerSaveException {
        String data = getAllTasksAsCSV();
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(saveFilePath.toString(), StandardCharsets.UTF_8))) {
            writer.write(data);
        } catch (IOException e) {
            System.out.println("Произошла ошибка сохранения в файл: " + e.getMessage());
        }
    }

    private String getAllTasksAsCSV() {
        StringBuilder result = new StringBuilder();
        ArrayList<Task> allTasks = new ArrayList<>();
        result.append(String.join(",", headers));
        result.append("\n");

        allTasks.addAll(getTasks());
        allTasks.addAll(getEpics());
        allTasks.addAll(getSubTasks());

        for (Task task : allTasks) {
            if (task instanceof Epic epic) {
                result.append(epic.toCSV(headers.length)).append(",");
            } else if (task instanceof SubTask subTask) {
                result.append(subTask.toCSV(headers.length));
            } else {
                result.append(task.toCSV(headers.length)).append(",");
            }
            result.append("\n");
        }

        return result.toString();
    }

    @Override
    public void removeAllTasks() {
        super.removeAllTasks();
        save();
    }

    @Override
    public void removeAllEpics() {
        super.removeAllEpics();
        save();
    }

    @Override
    public void removeAllSubTasks() {
        super.removeAllSubTasks();
        save();
    }

    @Override
    public int createTask(Task task) {
        int id = super.createTask(task);
        save();
        return id;
    }

    @Override
    public int createEpic(Epic epic) {
        int id = super.createTask(epic);
        save();
        return id;
    }

    @Override
    public int createSubTask(SubTask subTask) {
        int id = super.createTask(subTask);
        save();
        return id;
    }

    @Override
    public void updateTask(Task task, int id) {
        super.updateTask(task, id);
        save();
    }

    @Override
    public void updateEpic(Epic epic, int id) {
        super.updateEpic(epic, id);
        save();
    }

    @Override
    public void updateSubTask(SubTask subTask, int id) {
        super.updateSubTask(subTask, id);
        save();
    }

    @Override
    public void removeTaskById(int id) {
        super.removeTaskById(id);
        save();
    }

    @Override
    public void removeEpicById(int id) {
        super.removeEpicById(id);
        save();
    }

    @Override
    public void removeSubTaskById(int id) {
        super.removeSubTaskById(id);
        save();
    }
}
