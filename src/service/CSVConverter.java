package service;

import java.util.*;

public class CSVConverter {

    public static String toCSV(String[] headers, String[]... data) throws IllegalArgumentException {
        StringBuilder result = new StringBuilder();
        result.append(String.join(",", headers));

        for (String[] s : data) {
            if (s.length > headers.length) {
                throw new IllegalArgumentException("Ошибка конвертации в CSV: количество полей в строке превышает"
                        + " указанное количество заголовков - " + headers.length + ". Строка, вызвавшая ошибку: "
                        + "\n" + Arrays.toString(s));
            }
            result.append(String.join(",", s));
            result.append("\n");
        }

        return result.toString();
    }

    public static String singleLineToCSV(String[] headers, String[] data) {
        StringBuilder result = new StringBuilder();

        if (data.length > headers.length) {
            throw new IllegalArgumentException("Ошибка конвертации в CSV: количество полей в строке превышает"
                    + " указанное количество заголовков - " + headers.length + ". Строка, вызвавшая ошибку: "
                    + "\n" + Arrays.toString(data));
        }
        result.append(String.join(",", data));
        result.append("\n");

        return result.toString();
    }

    public static Map<String, String[]> fromCSV(String data) throws IllegalArgumentException {
        Map<String, ArrayList<String>> mappedData = new LinkedHashMap<>();
        Map<String, String[]> result = new LinkedHashMap<>();
        String[] dataArray = data.split("\n");
        String[] headers = dataArray[0].split(",");
        int headersCount = headers.length;
        int currentHeader = 0;

        for (String header : headers) {
            mappedData.put(header, new ArrayList<>());
        }

        for (int i = 1; i < dataArray.length; i++) {
            String[] lineData = dataArray[i].split(",");
            if (lineData.length != headersCount) {
                throw new IllegalArgumentException("Ошибка чтения из CSV: количество полей в строке " + i
                        + " не соответствует количеству заголовков.");
            }

            for (String field : lineData) {
                mappedData.get(headers[currentHeader]).add(field);
                if (currentHeader == headersCount - 1) {
                    currentHeader = 0;
                } else {
                    currentHeader++;
                }
            }
        }

        for (Map.Entry<String, ArrayList<String>> entry : mappedData.entrySet()) {
            ArrayList<String> value = entry.getValue();
            result.put(entry.getKey(), value.toArray(new String[value.size()]));
        }

        return result;
    }
}
