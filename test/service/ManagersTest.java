package service;

import java.io.*;
import java.nio.file.Path;
import java.util.*;

import org.junit.jupiter.api.*;

import model.*;
import service.exceptions.ManagerLoadException;
import service.utils.Utils;

import static org.junit.jupiter.api.Assertions.*;

public class ManagersTest {

    @Test
    @DisplayName("Класс Managers возвращает корректно работающий экземпляр менеджера задач")
    void shouldReturnCorrectlyInitializedTaskManager() {
        TaskManager taskManager = Managers.getDefault();
        assertNotNull(taskManager.getTasks());
        assertEquals(new ArrayList<Task>(), taskManager.getTasks());

        assertNotNull(taskManager.getEpics());
        assertEquals(new ArrayList<Epic>(), taskManager.getEpics());

        assertNotNull(taskManager.getSubTasks());
        assertEquals(new ArrayList<SubTask>(), taskManager.getSubTasks());

        assertNotNull(taskManager.getHistory());
        assertEquals(new LinkedList<>(), taskManager.getHistory());
    }

    @Test
    @DisplayName("Класс Managers возвращает корректно работающий экземпляр менеджера истории")
    void shouldReturnCorrectlyInitializedHistoryManager() {
        HistoryManager historyManager = Managers.getDefaultHistory();

        assertNotNull(historyManager.getHistory());
        assertEquals(new LinkedList<>(), historyManager.getHistory());
    }

    @Test
    @DisplayName("Метод getFileBacked() возвращает инициализированный экземпляр менеджера задач")
    void shouldReturnCorrectlyInitializedFileBackedManager() throws IOException {
        File testFile = File.createTempFile("test", "csv");
        Path testFilePath = testFile.toPath();
        String testFileString = testFile.toString();
        String taskCSV = "1,TASK,Task1,NEW,Description task1,";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(testFile))) {
            writer.write("id,type,name,status,description,epic");
            writer.write("\n");
            writer.write(taskCSV);
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileBackedTaskManager managerFromFile = Managers.getFileBacked(testFile);
        FileBackedTaskManager managerFromPath = Managers.getFileBacked(testFilePath);
        FileBackedTaskManager managerFromString = Managers.getFileBacked(testFileString);

        String expectedFromFile = Utils.csvCommaEqualizer(6,
                managerFromFile.getTaskById(1).toCSV(6));
        String expectedFromPath = Utils.csvCommaEqualizer(6,
                managerFromPath.getTaskById(1).toCSV(6));
        String expectedFromString = Utils.csvCommaEqualizer(6,
                managerFromString.getTaskById(1).toCSV(6));

        assertEquals(expectedFromFile, taskCSV);
        assertEquals(expectedFromPath, taskCSV);
        assertEquals(expectedFromString, taskCSV);
    }

    @Test
    @DisplayName("Метод getFileBacked выбрасывает исключение, если вместо файла указан путь на директорию")
    void shouldThrowManagerLoadException() throws IOException {
        File dir = new File(System.getProperty("user.home"));

        assertThrows(ManagerLoadException.class, () -> Managers.getFileBacked(dir));
    }

    @Test
    @DisplayName("Метод loadFromFile корректно загружает данные из CSV файла")
    void shouldCorrectlyLoadFromCSVFile() throws IOException {
        File testFile = File.createTempFile("test", "csv");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(testFile))) {
            writer.write("""
                    id,type,name,status,description,epic
                    1,TASK,Task1,NEW,Description task1,
                    2,EPIC,Epic2,DONE,Description epic2,
                    3,SUBTASK,Sub Task2,DONE,Description sub task3,2
                    """);
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileBackedTaskManager manager = Managers.getFileBacked(testFile);

        assertEquals("3,SUBTASK,Sub Task2,DONE,Description sub task3,2",
                manager.getSubTaskById(3).toCSV(6));
    }

    @Test
    @DisplayName("Метод loadFromFile выбрасывает исключение ManagerLoadException при невалидном CSV")
    void shouldThrowManagerLoadExceptionForInvalidCSV() throws IOException {
        File testFile = File.createTempFile("test", "csv");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(testFile))) {
            writer.write("""
                    id,type,name,status,description,epic
                    1,TASK,Task1,NEW,Description task1,
                    ,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
                    """);
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertThrows(ManagerLoadException.class, () -> Managers.getFileBacked(testFile));
    }
}
