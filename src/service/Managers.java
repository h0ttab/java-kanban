package service;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;

import service.exceptions.ManagerLoadException;
import service.utils.Utils;

public class Managers {

    public static TaskManager getDefault() {
        IdGenerator idGenerator = new IdGenerator();
        HistoryManager historyManager = getDefaultHistory();

        return new InMemoryTaskManager(idGenerator, historyManager);
    }

    public static FileBackedTaskManager getFileBacked(String fileName) throws IOException, ManagerLoadException {
        return getFileBacked(Paths.get(fileName));
    }

    public static FileBackedTaskManager getFileBacked(File file) throws IOException, ManagerLoadException {
        return getFileBacked(file.toPath());
    }

    public static FileBackedTaskManager getFileBacked(Path filePath) throws IOException, ManagerLoadException {
        if (Files.notExists(filePath)) {
            Files.createFile(filePath);
            return new FileBackedTaskManager(new IdGenerator(), getDefaultHistory(), filePath);
        }

        if (!Files.isRegularFile(filePath)) {
            throw new ManagerLoadException(filePath + " не является файлом.");
        }

        return loadFromFile(filePath.toFile());
    }

    private static FileBackedTaskManager loadFromFile(File file) throws ManagerLoadException {
        FileBackedTaskManager manager = new FileBackedTaskManager(
                new IdGenerator(), getDefaultHistory(), file.toPath());
        ArrayList<String[]> tasksData = Utils.parseCSV(file);

        for (int i = 1; i < tasksData.size(); i++) {
            String[] task = tasksData.get(i);

            try {
                switch (task[1]) {
                    case "TASK" -> manager.loadTask(task);
                    case "EPIC" -> manager.loadEpic(task);
                    case "SUBTASK" -> manager.loadSubTask(task);
                    default -> throw new IllegalArgumentException();
                }
            } catch (NullPointerException | ArrayIndexOutOfBoundsException | IllegalArgumentException e) {
                System.out.println("Во время чтения файла сохранения произошла ошибка: " + e);
                throw new ManagerLoadException("Ошибка при чтении CSV строки: " + e.getMessage());
            }
        }

        return manager;
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
