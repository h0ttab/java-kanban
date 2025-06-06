package service;

import java.io.IOException;
import java.nio.file.*;

public class Managers {

    public static TaskManager getDefault() {
        IdGenerator idGenerator = new IdGenerator();
        HistoryManager historyManager = getDefaultHistory();

        return new InMemoryTaskManager(idGenerator, historyManager);
    }

    public static TaskManager getFileBacked(String fileName) {
        IdGenerator idGenerator = new IdGenerator();
        HistoryManager historyManager = getDefaultHistory();

        Path saveFilePath = Paths.get("resources", fileName);

        if (Files.notExists(saveFilePath)) {
            try {
                Files.createFile(saveFilePath);
            } catch (IOException e) {
                System.out.println("Ошибка при создании файла сохранения для FileBackedTaskManager.");
            }
        }

        return new FileBackedTaskManager(idGenerator, historyManager, saveFilePath);
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
