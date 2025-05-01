package service;

public class Managers {

    public static TaskManager getDefault() {
        IdGenerator idGenerator = new IdGenerator();
        HistoryManager historyManager = getDefaultHistory();

        return new InMemoryTaskManager(idGenerator, historyManager);
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
