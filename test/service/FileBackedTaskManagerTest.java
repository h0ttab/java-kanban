package service;

import java.io.*;
import java.lang.reflect.*;

import org.junit.jupiter.api.*;

import model.*;

import static org.junit.jupiter.api.Assertions.*;

public class FileBackedTaskManagerTest {
    File testSaveFile;
    FileBackedTaskManager manager;
    Task task;
    Epic epic;
    SubTask subTask;

    void initTestFile() throws IOException {
        testSaveFile = File.createTempFile("test", "csv");
        testSaveFile.deleteOnExit();
        String testCSVData = """
                id,type,name,status,description,epic
                1,TASK,Task1,NEW,Description task1,
                2,EPIC,Epic2,DONE,Description epic2,
                3,SUBTASK,Sub Task2,DONE,Description sub task3,2
                """;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(testSaveFile))) {
            writer.write(testCSVData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    String readFileContent(File file) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            StringBuilder fileContent = new StringBuilder();
            while (reader.ready()) {
                fileContent.append(reader.readLine()).append("\n");
            }
            return fileContent.toString();
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }
    }

    @BeforeEach
    void initManager() throws IOException {
        initTestFile();
        manager = Managers.getFileBacked(testSaveFile);
        task = new Task("Задача", "Описание задачи", Status.NEW);
        task.setId(1);

        epic = new Epic("Эпик", "Описание эпика");
        epic.setId(2);

        subTask = new SubTask("Подзадача", "Описание подзадачи", Status.DONE, 2);
        subTask.setId(3);
    }

    @Test
    @DisplayName("Метод getAllTasksAsCSV возвращает валидное CSV представление всех задач")
    void shouldReturnAllTasksInValidCSVString() throws NoSuchMethodException,
            InvocationTargetException, IllegalAccessException {
        // Предварительно очистим менеджер от задач, подгруженных из файла при инициализации
        manager.removeAllTasks();
        manager.removeAllEpics();

        manager.createTask(task);
        manager.createEpic(epic);
        manager.createSubTask(subTask);

        Method method = manager.getClass().getDeclaredMethod("getAllTasksAsCSV");
        method.setAccessible(true);

        String expectedCSV = """
                id,type,name,status,description,epic
                1,TASK,Задача,NEW,Описание задачи,
                2,EPIC,Эпик,NEW,Описание эпика,
                3,SUBTASK,Подзадача,DONE,Описание подзадачи,2
                """;
        String actualCSV = (String) method.invoke(manager);

        assertEquals(expectedCSV, actualCSV);
    }

    @Test
    @DisplayName("Реализации createTask/Epic/Subtask, принимающие id в параметре, корректно задают переданный id")
    void shouldSetCorrectId() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method taskMethod = manager.getClass().getDeclaredMethod("createTask", Task.class, int.class);
        Method epicMethod = manager.getClass().getDeclaredMethod("createEpic", Epic.class, int.class);
        Method subTaskMethod = manager.getClass().getDeclaredMethod("createSubTask", SubTask.class, int.class);
        SubTask subTaskTest = new SubTask("Поздача тест", "Описание подзадачи",
                Status.DONE, 100);
        taskMethod.setAccessible(true);
        epicMethod.setAccessible(true);
        subTaskMethod.setAccessible(true);

        taskMethod.invoke(manager, task, 99);
        epicMethod.invoke(manager, epic, 100);
        subTaskMethod.invoke(manager, subTaskTest, 101);

        assertEquals(manager.getTaskById(99), task);
        assertEquals(manager.getEpicById(100), epic);
        assertEquals(manager.getSubTaskById(101), subTaskTest);
    }

    @Test
    @DisplayName("Методы loadTask/Epic/SubTask корректно создают задачи из массива полей, полученного из CSV")
    void shouldLoadTasksCorrectly() {
        String[] taskCSV = new String[]{"100", "TASK", "Название задачи", "NEW", "Описание задачи"};
        manager.loadTask(taskCSV);
        Task task = new Task("Название задачи", "Описание задачи", Status.NEW);
        task.setId(100);

        String[] epicCSV = new String[]{"101", "EPIC", "Название эпика", "DONE", "Описание эпика"};
        manager.loadEpic(epicCSV);
        Epic epic = new Epic("Название эпика", "Описание эпика", Status.DONE);
        epic.setId(101);

        String[] subTaskCSV = new String[]{"102", "SUBTASK", "Название подзадачи", "NEW", "Описание подзадачи", "101"};
        manager.loadSubTask(subTaskCSV);
        SubTask subTask = new SubTask("Название подзадачи", "Описание подзадачи",
                Status.NEW, 101);
        subTask.setId(102);
        epic.addSubTask(102, Status.NEW);

        assertEquals(manager.getTaskById(100), task);
        assertEquals(manager.getEpicById(101), epic);
        assertEquals(manager.getSubTaskById(102), subTask);
    }

    @Test
    @DisplayName("Переопределённые реализации стандартных методов менеджера корректно автосохраняют задачи в файл")
    void shouldAutosaveCorrectly() {
        String taskCSV = "4,TASK,Название задачи,NEW,описание задачи,";
        String subtaskCSV = "5,SUBTASK,Подзадача,NEW,описание,2";
        Task testTask = new Task("Название задачи", "описание задачи", Status.NEW);
        SubTask testSubTask = new SubTask("Подзадача", "описание", Status.NEW, 2);
        try {
            String contentBeforeSave = readFileContent(testSaveFile);

            assertFalse(contentBeforeSave.contains(taskCSV));
            assertFalse(contentBeforeSave.contains(subtaskCSV));

            manager.createTask(testTask);
            manager.createSubTask(testSubTask);
            String contentAfterSave = readFileContent(testSaveFile);

            assertTrue(contentAfterSave.contains(taskCSV));
            assertTrue(contentAfterSave.contains(subtaskCSV));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Менеджер корректно инициализируется и работает, если входной файл пуст")
    void shouldHandleEmptySaveFile() {
        try {
            File emptyFile = File.createTempFile("empty", "csv");
            emptyFile.deleteOnExit();
            FileBackedTaskManager manager = new FileBackedTaskManager(new IdGenerator(),
                    Managers.getDefaultHistory(), emptyFile);
            String taskCsv = "1,TASK,Title,NEW,Description,";

            manager.createTask(new Task("Title", "Description", Status.NEW));

            assertTrue(readFileContent(emptyFile).contains(taskCsv));
        } catch (IOException e) {
            fail();
        }

    }
}
