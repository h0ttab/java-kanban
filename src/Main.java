public class Main {
    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();
        TaskService taskService = new TaskService(taskManager);

        taskService.createTask(new Task("Помыть посуду", "Помыть всю посуду", Status.NEW)); //id=1
        taskService.createTask(new Task("Поужинать", "Приготовить ужин и поужинать", Status.NEW)); // id=2
        Epic epic1 = new Epic("Учёба в практикуме", "Потратить не менее 2 часов на учёбу");
        taskService.createEpic(epic1);// id=3
        taskService.createSubTask(epic1,
                new SubTask("Найти нужную тему", "Найти последнюю пройденную тему", Status.NEW)); //id=4
        taskService.createSubTask(epic1,
                new SubTask("Изучить тему", "Внимательно изучить тему", Status.NEW));//id=5
        Epic epic2 = new Epic("Тестовый эпик", "Просто ещё один эпик"); //id=6
        taskService.createEpic(epic2);
        taskService.createSubTask(epic2, new SubTask("Подзадача", "Ещё одна подзадача", Status.NEW));

        System.out.println(taskManager.getTaskList());
        System.out.println(taskManager.getEpicList());
        System.out.println(taskManager.getSubTaskList());
        System.out.println();

        taskManager.getTask(1).setStatus(Status.DONE);
        taskManager.getSubTask(4).setStatus(Status.DONE);

        System.out.println(taskManager.getTaskList());
        System.out.println(taskManager.getEpicList());
        System.out.println(taskManager.getSubTaskList());
        System.out.println();

        taskManager.removeEpic(6);
        taskManager.removeSubTask(4);
        taskManager.removeSubTask(5);

        System.out.println(taskManager.getTaskList());
        System.out.println(taskManager.getEpicList());
        System.out.println(taskManager.getSubTaskList());
    }
}
