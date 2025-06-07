package service.utils;

public class StringUtils {

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
}
