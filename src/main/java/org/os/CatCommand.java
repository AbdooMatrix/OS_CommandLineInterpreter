package org.os;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CatCommand {
    public String cat(String[] filePaths) {
        StringBuilder output = new StringBuilder();

        for (String filePathStr : filePaths) {
            // Check for empty file path
            if (filePathStr == null || filePathStr.trim().isEmpty()) {
                output.append("Error: No file path provided for one of the files.\n");
                continue;
            }

            // Normalize and create the Path object
            Path filePath = Paths.get(filePathStr).normalize();

            // Check if the file exists
            if (!Files.exists(filePath)) {
                output.append("Error: File does not exist: ").append(filePath).append("\n");
                continue;
            }

            // Check if the path is a file
            if (!Files.isRegularFile(filePath)) {
                output.append("Error: Path is not a file: ").append(filePath).append("\n");
                continue;
            }

            // Read and append each line from the file to the output
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath.toFile()))) {
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
