public class TaskService {
    private final TaskManager taskManager;

    public TaskService(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    public void createTask(Task task){
        int id = taskManager.getUniqueId();
        taskManager.createTask(task, id);
    }

    public void createEpic(Epic epic) {
        int id = taskManager.getUniqueId();
        taskManager.createEpic(epic, id);
    }

    public void createSubTask(Epic epic, SubTask subTask) {
        int id = taskManager.getUniqueId();
        subTask.setEpic(epic);
        epic.addSubTask(subTask, id);
    }
}
