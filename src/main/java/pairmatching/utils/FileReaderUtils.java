package pairmatching.utils;

import pairmatching.Application;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

public class FileReaderUtils {

    private FileReaderUtils() {

    }

    public static List<String> readMarkdownFile(String filePath) {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        Objects.requireNonNull(Application.class.getClassLoader().getResourceAsStream(filePath)),
                        StandardCharsets.UTF_8
                )
        )) {
            return reader.lines().toList();
        } catch (Exception e) {
            throw new IllegalStateException("Failed to read file : " + filePath, e);
        }
    }
}
