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

        allTasks.addAll(getTasks());
        allTasks.addAll(getEpics());
        allTasks.addAll(getSubTasks());

        for (Task task : allTasks) {
            if (task instanceof Epic) {
                Epic epic = (Epic) task;
                result.append(epic.toCSV(headers.length));
            } else if (task instanceof SubTask) {
                SubTask subTask = (SubTask) task;
                result.append(subTask.toCSV(headers.length));
            } else {
                result.append(task.toCSV(headers.length));
            }
        }

        return result.toString();
    }
}
