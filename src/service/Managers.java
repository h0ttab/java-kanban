package service;

import java.io.IOException;
import java.nio.file.*;
import java.io.File;

public class Managers {

    public static TaskManager getDefault() {
        IdGenerator idGenerator = new IdGenerator();
        HistoryManager historyManager = getDefaultHistory();

        return new InMemoryTaskManager(idGenerator, historyManager);
    }

    public static TaskManager getFileBacked(String fileName) {
        return getFileBacked(Paths.get(fileName));
    }

    public static TaskManager getFileBacked(File file) {
        return getFileBacked(file.toPath());
    }

    public static TaskManager getFileBacked(Path filePath) {
        IdGenerator idGenerator = new IdGenerator();
        HistoryManager historyManager = getDefaultHistory();

        if (Files.notExists(filePath)) {
            try {
                Files.createFile(filePath);
            } catch (IOException e) {
                System.out.println("Ошибка при создании файла сохранения для FileBackedTaskManager.");
            }
        }

        try {
            if (!Files.isRegularFile(filePath)) {
                throw new IOException("Указанный путь сохранения для FileBackedTaskManager не является файлом.");
            }
        } catch (IOException e) {
            System.out.println("Ошибка получения файла сохранения: " + e.getMessage());
        }

        return new FileBackedTaskManager(idGenerator, historyManager, filePath);
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
