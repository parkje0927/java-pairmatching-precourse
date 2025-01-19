package pairmatching.utils;

public class InputValidationUtils {

    private InputValidationUtils() {
    }

    public static void validateInput(String input) {
        if (input == null || input.isEmpty()) {
            throw new IllegalArgumentException("입력 데이터가 없습니다.");
        }
    }

    public static String[] validateAndSplitInput(String input, int size) {
        if (input == null || input.isEmpty()) {
            throw new IllegalArgumentException("입력 데이터가 없습니다.");
        }

        String[] inputData = input.split(", ");
        if (inputData.length != size) {
            throw new IllegalArgumentException("입력한 데이터가 부족합니다.");
        }

        return inputData;
    }
}
