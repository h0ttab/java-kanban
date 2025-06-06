package service;

import java.nio.file.Path;
import java.nio.file.Files;

public class FileBackedTaskManager extends InMemoryTaskManager implements TaskManager {
    private final Path saveFilePath;

    public FileBackedTaskManager(IdGenerator idGenerator, HistoryManager historyManager, Path saveFilePath ) {
        super(idGenerator, historyManager);
        this.saveFilePath = saveFilePath;
    }
}
