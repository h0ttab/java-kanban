package service.utils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class Utils {

    private static int substringCounter(String s, String substring) {
        int substringCount = 0;
        int currentIndexOf = 0;

        while (currentIndexOf != s.lastIndexOf(substring)) {
            substringCount++;
            currentIndexOf = s.indexOf(substring, currentIndexOf + 1);
        }

        return substringCount;
    }

    public static String csvCommaEqualizer(int commaExpectedCount, String csvString) {
        StringBuilder result = new StringBuilder(csvString);

        int commaCountDiff = commaExpectedCount - substringCounter(csvString, ",");

        return result.append(",".repeat(Math.max(0, commaCountDiff))).toString();
    }

    public static ArrayList<String[]> parseCSV(File file) {
        ArrayList<String[]> data = new ArrayList<>();

        try(BufferedReader reader = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8))) {
            while (reader.ready()) {
                data.add(reader.readLine().split(","));
            }
        } catch (IOException e) {
            System.out.println("Произошла ошибка при чтении файла сохранения: " + e.getMessage());
        }

        return data;
    }
}
