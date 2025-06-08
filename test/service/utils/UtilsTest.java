package service.utils;

import org.junit.jupiter.api.*;

import java.io.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class UtilsTest {

    @Test
    @DisplayName("Метод подсчёта количества вхождений подстроки работает корректно")
    void shouldCountSubstringsCorrectly() {
        String testStringA = "1.2.3.4.5.";
        int expectedCountA = 5;
        int actualCountA = Utils.substringCounter(testStringA, ".");

        String testStringB = "....";
        int expectedCountB = 4;
        int actualCountB = Utils.substringCounter(testStringB, ".");

        String testStringC = ".";
        int expectedCountC = 1;
        int actualCountC = Utils.substringCounter(testStringC, ".");

        String testStringD = "";
        int expectedCountD = 0;
        int actualCountD = Utils.substringCounter(testStringD, ".");

        assertEquals(expectedCountA, actualCountA);
        assertEquals(expectedCountB, actualCountB);
        assertEquals(expectedCountC, actualCountC);
        assertEquals(expectedCountD, actualCountD);
    }

    @Test
    @DisplayName("Метод выравнивания количества запятых в CSV строке должен работать корректно")
    void shouldEqualizeCommasInCSVStringCorrectly() {
        String exampleCSVString = "value,anotherValue,1,false";
        String expectedString = "value,anotherValue,1,false,,";

        assertEquals(expectedString, Utils.csvCommaEqualizer(6, exampleCSVString));
    }

    @Test
    @DisplayName("Метод чтения из CSV должен корректно парсить CSV-файл")
    void shouldParseCSVFileCorrectly() throws IOException {
        File testFile = File.createTempFile("test", ".csv");
        testFile.deleteOnExit();
        String testCSV = "value_a,value_b,value_c\nA,B,C";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(testFile.toString()))) {
            writer.write(testCSV);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ArrayList<String[]> parsedCSV = Utils.parseCSV(testFile);
        String[] headersExpected = new String[]{"value_a", "value_b", "value_c"};
        String[] dataExpected = new String[]{"A", "B", "C"};

        assertArrayEquals(headersExpected, parsedCSV.getFirst());
        assertArrayEquals(dataExpected, parsedCSV.get(1));
    }
}
