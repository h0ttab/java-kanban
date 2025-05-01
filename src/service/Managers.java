package service;

public class Managers {

    public static TaskManager getDefault() {
        IdGenerator idGenerator = new IdGenerator();
        return new InMemoryTaskManager(idGenerator);
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
