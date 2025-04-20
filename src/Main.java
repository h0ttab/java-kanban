public class Main {

    public static void main(String[] args) {
        System.out.println("Поехали!");
        TaskManager taskManager = new TaskManager();

        Task task1 = new Task("Помыть посуду",
                "Помыть всю посуду в раковине, и не забыть про сковородку", Status.NEW);
        Task task2 = new Task("Позаниматься учёбой", "Уделить 1.5 часа на учёбу в практикуме",
                Status.IN_PROGRESS);
        taskManager.addTask(task1);
        taskManager.addTask(task2);

        Epic epic1 = new Epic("Сходить за продуктами", "Сходить в магазин и закупиться продуктами");
        SubTask subTask1 = new SubTask("Список продуктов", "Продумать и составить список продуктов",
                Status.NEW, epic1.getId());
        SubTask subTask2 = new SubTask("Сходить в магазин", "Отправить в продуктовый магазин" +
                " и закупиться всеми продуктами по списку", Status.NEW, epic1.getId());

        Epic epic2 = new Epic("Ответить на сообщения", "Зайти в WhatsApp и ответить всем друзьям");
        SubTask subTask3 = new SubTask("Ответить Сэму", "Ответить на 4 сообщение от Сэма", Status.NEW,
                epic2.getId());

        System.out.println();
    }
}
