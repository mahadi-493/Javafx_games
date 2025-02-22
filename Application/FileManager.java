package application;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class FileManager {

    private static final String FILE_PATH = "E:\\Eclipse\\DemoGame\\src\\user_credentials.txt";

    public static boolean createUser(String username, String password) {
        String userRecord = username + ":" + password + System.lineSeparator();
        try {
            Files.write(Path.of(FILE_PATH), userRecord.getBytes(), StandardOpenOption.APPEND);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isValidLogin(String username, String password) {
        try {
            List<String> lines = Files.readAllLines(Path.of(FILE_PATH));
            for (String line : lines) {
                String[] parts = line.split(":");
                if (parts.length == 2 && parts[0].equals(username) && parts[1].equals(password)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}

