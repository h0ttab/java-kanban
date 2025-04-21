public class Main {

    public static void main(String[] args) {
        System.out.println("Поехали!");

        Task task1 = new Task("Помыть посуду",
                "Помыть всю посуду в раковине, и не забыть про сковородку", Status.NEW);
        TaskManager.addTask(task1);

        Task task2 = new Task("Позаниматься учёбой", "Уделить 1.5 часа на учёбу в практикуме",
                Status.IN_PROGRESS);
        TaskManager.addTask(task2);

        Epic epic1 = new Epic("Сходить за продуктами", "Сходить в магазин и закупиться продуктами");
        TaskManager.addEpic(epic1);

        SubTask subTask1 = new SubTask("Список продуктов", "Продумать и составить список продуктов",
                Status.DONE, epic1.getId());
        TaskManager.addSubTask(subTask1);

        SubTask subTask2 = new SubTask("Сходить в магазин", "Отправить в продуктовый магазин" +
                " и закупиться всеми продуктами по списку", Status.NEW, epic1.getId());
        TaskManager.addSubTask(subTask2);

        Epic epic2 = new Epic("Ответить на сообщения", "Зайти в WhatsApp и ответить всем друзьям");
        TaskManager.addEpic(epic2);

        SubTask subTask3 = new SubTask("Ответить Сэму", "Ответить на 4 сообщение от Сэма", Status.NEW,
                epic2.getId());
        TaskManager.addSubTask(subTask3);

        System.out.println(TaskManager.getTasks());
        System.out.println(TaskManager.getEpics());
        System.out.println(TaskManager.getSubTasks());

        task1.setStatus(Status.DONE);
        subTask1.setStatus(Status.IN_PROGRESS);

        System.out.println();
        System.out.println(TaskManager.getTasks());
        System.out.println(TaskManager.getEpics());
        System.out.println(TaskManager.getSubTasks());

        TaskManager.removeTaskById(1);
        TaskManager.removeSubTaskById(4);
        TaskManager.removeEpicById(6);

        System.out.println();
        System.out.println(TaskManager.getTasks());
        System.out.println(TaskManager.getEpics());
        System.out.println(TaskManager.getSubTasks());
    }
}
