package org.os;

import java.io.File;
import java.util.Arrays;

public class LsACommand {

    // Method to list all files and directories in a given path
    public String listAllFiles(String path) {
        // Use the current directory if the path is null
        File directory = new File(path != null ? path : ".");

        // StringBuilder to hold the output (file names or error message)
        StringBuilder output = new StringBuilder();

        // Check if the directory exists and is a valid directory
        if (directory.exists() && directory.isDirectory()) {
            // Get the list of files and directories in the specified path
            File[] files = directory.listFiles();

            // If files are not null, append each file's name to the output
            if (files != null) {
                Arrays.stream(files).forEach(file -> output.append(file.getName()).append("\n"));
            }
        } else {
            // If the path is not a valid directory, append an error message
            output.append("Error: Directory not found or is not a directory.\n");
        }

        // Return the output string
        return output.toString();
    }
}
