package service.utils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class Utils {

    public static int substringCounter(String s, String substring) {
        int substringCount = 0;
        int currentIndex = 0;

        if (!s.contains(substring)) {
            return 0;
        }

        while (s.indexOf(substring, currentIndex) != -1) {
            substringCount++;
            currentIndex = s.indexOf(substring, currentIndex) + 1;
        }

        return substringCount;
    }

    public static String csvCommaEqualizer(int headersCount, String csvString) {
        StringBuilder result = new StringBuilder(csvString);

        int commaCountDiff = headersCount - 1 - substringCounter(csvString, ",");

        return result.append(",".repeat(Math.max(0, commaCountDiff))).toString();
    }

    public static ArrayList<String[]> parseCSV(File file) {
        ArrayList<String[]> data = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8))) {
            while (reader.ready()) {
                data.add(reader.readLine().split(","));
            }
        } catch (IOException e) {
            System.out.println("Произошла ошибка при чтении файла сохранения: " + e.getMessage());
        }

        return data;
    }
}
