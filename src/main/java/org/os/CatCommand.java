package org.os;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CatCommand {
    public String cat(String[] filePaths) {
        StringBuilder output = new StringBuilder();

        for (String filePathStr : filePaths) {
            if (filePathStr == null || filePathStr.trim().isEmpty()) {
                output.append("Error: No file path provided for one of the files.\n");
                continue;
            }

            Path filePath = Paths.get(filePathStr); // Treat each input as a full or relative path

            // Read and print each line from the file
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath.toString()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    output.append(line).append("\n");
                }
            } catch (IOException e) {
                output.append("Error reading file ").append(filePath).append(": ").append(e.getMessage()).append("\n");
            }
            output.append("\n"); // Add space between file contents
        }

        return output.toString(); // Return all file contents or errors as a single string
    }
}
