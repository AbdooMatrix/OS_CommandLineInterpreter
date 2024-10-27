package org.os;

import java.io.File;
import java.util.Arrays;

public class LsACommand {
    public String listAllFiles(String path) {
        File directory = new File(path != null ? path : ".");
        StringBuilder output = new StringBuilder();

        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                Arrays.stream(files)
                        .forEach(file -> output.append(file.getName()).append("\n"));
            }
        } else {
            output.append("Error: Directory not found or is not a directory.\n");
        }
        return output.toString();
    }
}
