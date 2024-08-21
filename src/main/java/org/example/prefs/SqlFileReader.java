package org.example.prefs;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SqlFileReader {
    public static String readSqlFile(String filePath) {
        Path path = Paths.get(filePath);
        if (!Files.exists(path) || !Files.isReadable(path)) {
            throw new IllegalArgumentException("Файл не существует или недоступен для чтения: " + filePath);
        }

        try {
            return Files.readString(path, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при чтении SQL файла: " + filePath, e);
        }
    }
}

